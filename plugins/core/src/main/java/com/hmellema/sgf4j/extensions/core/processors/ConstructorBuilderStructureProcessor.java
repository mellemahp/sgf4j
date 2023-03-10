package com.hmellema.sgf4j.extensions.core.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.traitprocessing.Processor;
import com.squareup.javapoet.*;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.StructureShape;

import javax.lang.model.element.Modifier;
import java.util.*;

public class ConstructorBuilderStructureProcessor implements Processor {
    private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.of(ShapeType.STRUCTURE);
    private static final String INPUT_TRAIT = "smithy.api#input";

    @Override
    public EnumSet<ShapeType> getSupportedShapeTypes() {
        return SUPPORTED_SHAPES;
    }

    @Override
    public Set<String> getSupportedTraitNames() {
        return Set.of("*");
    }

    @Override
    public void process(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader) {
        var structureShape = (StructureShape) shapeGenMetadata.getShape();
        if (structureShape.hasTrait(INPUT_TRAIT)) {
            // Do not create a non-default constructor or a builder for inputs.
            // Jackson or other serializer will handle setting the values on these objects
            // users should not be able to create a new instance of an input (i.e. input is immutable)
            return;
        }

        //
        var constructor = getConstructor(shapeGenMetadata, metaDataLoader);
        shapeGenMetadata.addClassAssociatedMethod(constructor);

        // create builder class
        var builderClass = getBuilder(shapeGenMetadata, metaDataLoader);
        shapeGenMetadata.addNestedClass(builderClass);

        // add static builder method
        shapeGenMetadata.addClassAssociatedMethod(getBuilderMethod(shapeGenMetadata));
    }

    private MethodSpec getBuilderMethod(ShapeGenMetadata shapeGenMetadata) {
        var builderType = getBuilderTypeName(shapeGenMetadata);
        return MethodSpec.methodBuilder("builder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addStatement("return new $T()", builderType)
                .returns(builderType)
                .build();
    }

    private MethodSpec getConstructor(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader) {
        var methodBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);

        var structShape = (StructureShape) shapeGenMetadata.getShape();
        for (var member : structShape.getAllMembers().values()) {
            var memberName = member.getMemberName();
            var memberMetadata = metaDataLoader.resolve(member.getId());

            // add parameter for member
            methodBuilder.addParameter(ParameterSpec.builder(memberMetadata.getTypeName(), memberName, Modifier.FINAL).build());

            // add a set statement for the member
            methodBuilder.addStatement("this.$L = $L", memberName, memberName);
        }


        return methodBuilder.build();
    }

    private TypeSpec getBuilder(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader) {
        var builderClassBuilder = TypeSpec.classBuilder("Builder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addJavadoc("Static builder class for {@link $T}", shapeGenMetadata.getTypeName());

        List<String> buildMethodParams = new ArrayList<>();
        var structureShape = (StructureShape) shapeGenMetadata.getShape();
        var memberShapes = structureShape.getAllMembers();
        for (var member : memberShapes.values()) {
            // Add a field for the member type
            var memberData = metaDataLoader.resolve(member.getId());
            var memberFieldType = memberData.asField();
            // Clear all annotations inside the builder
            var newMemberFieldType = FieldSpec.builder(
                    memberFieldType.type, memberFieldType.name, memberFieldType.modifiers.toArray(new Modifier[0])
            )
                    .addJavadoc("Value to use for the `{@code $L}` field.\n", member.getMemberName())
                    .build();

            builderClassBuilder.addField(newMemberFieldType);

            var field = member.getMemberName();
            // add for constructing actual builder
            buildMethodParams.add(field);

            builderClassBuilder.addMethod(
                    MethodSpec.methodBuilder(member.getMemberName())
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(ParameterSpec.builder(memberData.getTypeName(), field, Modifier.FINAL).build())
                            .beginControlFlow("if($L == null)", field)
                            .addStatement("throw new NullPointerException(\"$L cannot be null\")", field)
                            .endControlFlow()
                            .addStatement("this.$L = $L", field, field)
                            .addStatement("return this")
                            .returns(getBuilderTypeName(shapeGenMetadata))
                            .build()
            );
        }

        var buildReturnString = "return new $T(" + String.join(",", buildMethodParams) + ")";
        builderClassBuilder.addMethod(
                MethodSpec.methodBuilder("build")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(shapeGenMetadata.getTypeName())
                        .addStatement(buildReturnString, shapeGenMetadata.getTypeName())
                        .addJavadoc("Builds an instance of {@link $T }", shapeGenMetadata.getTypeName())
                        .addJavadoc("<p>")
                        .addJavadoc("The constructed object has its fields set the the values defined by calling the fluent setters on this {@link $T} class", getBuilderTypeName(shapeGenMetadata))
                        .addJavadoc("</p>")
                        .build()
        );

        return builderClassBuilder.build();
    }

    private TypeName getBuilderTypeName(ShapeGenMetadata shapeGenMetadata) {
        return ClassName.bestGuess(shapeGenMetadata.getTypeName().toString() + "." + "Builder");
    }
}

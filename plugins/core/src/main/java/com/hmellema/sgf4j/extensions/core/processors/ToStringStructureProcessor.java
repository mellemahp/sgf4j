package com.hmellema.sgf4j.extensions.core.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.traitprocessing.Processor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.StructureShape;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ToStringStructureProcessor implements Processor {
    private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.of(ShapeType.STRUCTURE);

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

        List<String> values = new ArrayList<>();
        List<String> statementStrings = new ArrayList<>();
        var startString = shapeGenMetadata.getShape().getId().getName() + "(";
        values.add(startString);
        values.add("+");

        boolean first = true;
        var structureShape = (StructureShape) shapeGenMetadata.getShape();
        for (var memberName : structureShape.getAllMembers().keySet()) {
            // string part
            if (first) {
                values.add(memberName + "=");
                first = false;
            } else {
                values.add("," + memberName + "=");
            }

            // literal part
            values.add("+ this." + memberName + " ");
            // corresponding values in the statement block
            statementStrings.add("$S $L ");
        }
        values.add("+");
        values.add(")");
        var startStatement = "return $S $L ";
        var statement = startStatement + String.join("+", statementStrings) + "$L $S";

        var toStringMethod = MethodSpec.methodBuilder("toString")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement(statement, values.toArray())
                .returns(ClassName.get(String.class))
                .build();

        shapeGenMetadata.addClassAssociatedMethod(toStringMethod);
    }
}

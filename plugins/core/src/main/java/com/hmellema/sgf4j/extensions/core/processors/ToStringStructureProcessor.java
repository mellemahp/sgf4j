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

        List<String> vals = new ArrayList<>();
        List<String> statementStrs = new ArrayList<>();
        var startString = shapeGenMetadata.getShape().getId().getName() + "(";
        vals.add(startString);
        vals.add("+");

        boolean first = true;
        var structureShape = (StructureShape) shapeGenMetadata.getShape();
        for (var memberName : structureShape.getAllMembers().keySet()) {
            // string part
            if (first) {
                vals.add(memberName + "=");
                first = false;
            } else {
                vals.add("," + memberName + "=");
            }

            // literal part
            vals.add("+ this." + memberName + " ");
            // corresponding vals in the statement block
            statementStrs.add("$S $L ");
        }
        vals.add("+");
        vals.add(")");
        var startStatement = "return $S $L ";
        var statement = startStatement + String.join("+", statementStrs) + "$L $S";


        var toStringMethod = MethodSpec.methodBuilder("toString")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement(statement, vals.toArray())
                .returns(ClassName.get(String.class))
                .build();

        shapeGenMetadata.addClassAssociatedMethod(toStringMethod);
    }
}

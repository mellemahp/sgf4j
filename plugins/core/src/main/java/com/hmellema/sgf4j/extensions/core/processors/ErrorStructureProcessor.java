package com.hmellema.sgf4j.extensions.core.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.*;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.EnumSet;
import java.util.Set;

public class ErrorStructureProcessor implements Processor {
    private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.of(ShapeType.STRUCTURE);

    private static final Set<String> SUPPORTED_TRAITS = Set.of("smithy.api#error");
    private final TypeName exceptionTypeName = ClassName.get(RuntimeException.class);

    @Override
    public EnumSet<ShapeType> getSupportedShapeTypes() {
        return SUPPORTED_SHAPES;
    }

    @Override
    public Set<String> getSupportedTraitNames() {
        return SUPPORTED_TRAITS;
    }

    @Override
    public void process(ShapeGenMetadata shapeGenMetadata, ShapeGenMetadataMap shapeGenMetadataMap) {
        shapeGenMetadata.setParentClassType(exceptionTypeName);
    }
}

package com.hmellema.sgf4j.extensions.jackson.processors;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.AnnotationSpec;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.StructureShape;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeserializationOrderProcessor implements Processor {
    private static final EnumSet<ShapeType> SUPPORTED_TYPES = EnumSet.of(ShapeType.STRUCTURE);
    private static final Set<String> SUPPORTED_TRAITS = Set.of("*");
    private static final String INPUT_TRAIT = "smithy.api#input";

    @Override
    public EnumSet<ShapeType> getSupportedShapeTypes() {
        return SUPPORTED_TYPES;
    }

    @Override
    public Set<String> getSupportedTraitNames() {
        return SUPPORTED_TRAITS;
    }

    @Override
    public void process(ShapeGenMetadata shapeGenMetadata, ShapeGenMetadataMap shapeGenMetadataMap) {
        if (shapeGenMetadata.getShape().hasTrait(INPUT_TRAIT)) {
            // We do not need to create serialization annotations on an input trait as it should
            // never be marshalled/serialized
            return;
        }

        var structureShape = (StructureShape) shapeGenMetadata.getShape();
        var memberNames = structureShape.getMemberNames();
        var memberNamesStr = memberNames.stream()
                .map(memberName -> String.format("\"%s\"", memberName))
                .collect(Collectors.joining(","));
        shapeGenMetadata.addClassAnnotation(
                AnnotationSpec.builder(JsonPropertyOrder.class)
                        .addMember("value", "{$L}", memberNamesStr)
                        .build()
        );
    }
}

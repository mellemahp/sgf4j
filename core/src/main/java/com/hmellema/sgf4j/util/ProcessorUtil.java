package com.hmellema.sgf4j.util;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.traitprocessing.Processor;
import software.amazon.smithy.model.shapes.Shape;

public interface ProcessorUtil {
    String ALL_TRAITS = "*";

    static boolean shouldExecuteProcessor(Processor processor, ShapeGenMetadata shapeGenMetadata) {
        return shapeTypeIsSupported(processor, shapeGenMetadata)
                && shapeHasProcessorSupportedTrait(processor, shapeGenMetadata.getShape());
    }

    static boolean shapeHasProcessorSupportedTrait(Processor processor, Shape shape) {
        return processor.getSupportedTraitNames().contains(ALL_TRAITS) ||
                processor.getSupportedTraitNames().stream()
                        .anyMatch(shape::hasTrait);
    }

    static boolean shapeTypeIsSupported(Processor processor, ShapeGenMetadata shapeGenMetadata) {
        return processor.getSupportedShapeTypes()
                .contains(shapeGenMetadata.getShapeType());

    }
}

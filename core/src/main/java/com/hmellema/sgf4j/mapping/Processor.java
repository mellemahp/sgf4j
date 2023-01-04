package com.hmellema.sgf4j.mapping;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.Set;

public interface Processor {
    /** Return the list of supported Smithy {@link software.amazon.smithy.model.shapes.ShapeType} that this processor will execute on
     *
     * @return Set of {@code ShapeType} values
     */
    Set<ShapeType> getSupportedShapeTypes();

    /** Return the list of supported Smithy {@link software.amazon.smithy.model.traits.Trait} names that this processor will execute on
     *
     * @return Set of trait names. If this should run on ALL traits, return {@code Set.of("*")}
     */
    Set<String> getSupportedTraitNames();

    void process(ShapeGenMetadata shapeGenMetadata);
}

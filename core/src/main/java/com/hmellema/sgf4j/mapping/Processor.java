package com.hmellema.sgf4j.mapping;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;

import java.util.Set;

public interface Processor {
    String ALL = "*";

    /** Return the list of supported Smithy {@link software.amazon.smithy.model.shapes.ShapeType} that this generator will execute on
     *
     * @return Set of {@code ShapeType} values represented as strings. If this should run on ALL shapes, return Set.of("*")
     */
    Set<String> getSupportedShapeTypes();

    /** Return the list of supported Smithy {@link software.amazon.smithy.model.traits.Trait} names that this generator will execute on
     *
     * @return Set of trait names. If this should run on ALL shapes, return {@code Set.of("*")}
     */
    Set<String> getSupportedTraitNames();

    void process(ShapeGenMetadata shapeGenMetadata);
}

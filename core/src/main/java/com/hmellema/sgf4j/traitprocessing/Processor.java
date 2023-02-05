package com.hmellema.sgf4j.traitprocessing;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.EnumSet;
import java.util.Set;

public interface Processor {
    /**
     * Wildcard indicating processor will run for any trait
     */
    String ALL_TRAITS = "*";

    /** Checks if the processor should run on a given ShapeGenMetadata node
     *
     * To be executed, the processor must support the given shape type and
     * must support either one of the traits on the object or all traits
     *
     * @param shapeGenMetadata shape metadata to process
     * @return if the processor will execute on the provided shape metadata
     */
    default boolean shouldExecute(ShapeGenMetadata shapeGenMetadata) {
            return getSupportedShapeTypes().contains(shapeGenMetadata.getShapeType())
                    && (getSupportedTraitNames().contains(ALL_TRAITS)
                        || getSupportedTraitNames().stream().anyMatch(shapeGenMetadata.getShape()::hasTrait));
    }

    /** Return the list of supported Smithy {@link software.amazon.smithy.model.shapes.ShapeType} that this processor will execute on
     *
     * @return Set of {@code ShapeType} values
     */
    EnumSet<ShapeType> getSupportedShapeTypes();

    /** Return the list of supported Smithy {@link software.amazon.smithy.model.traits.Trait} names that this processor will execute on
     *
     * @return Set of trait names. If this should run on ALL traits, return {@code Set.of("*")}
     */
    Set<String> getSupportedTraitNames();

    /** Method that determines how shape will be modified by processor
     *
     * @param shapeGenMetadata metadata object to process
     * @param metaDataLoader loader that provides resolved metadata objects
     */
    void process(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader);

}

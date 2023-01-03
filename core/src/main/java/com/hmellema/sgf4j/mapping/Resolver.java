package com.hmellema.sgf4j.mapping;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.Collections;
import java.util.Set;

public interface Resolver {
    /** Return the supported Smithy {@link software.amazon.smithy.model.shapes.ShapeType} that this resolver will be executed for
     *
     * @return Set of {@code ShapeType} values represented as a string.
     */
    ShapeType getSupportedShapeType();

    default Set<ShapeId> getDependentShapes(Shape shape) {
        return Collections.emptySet();
    }

    ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap);
}

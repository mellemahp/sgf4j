package com.hmellema.sgf4j.mapping;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public interface Resolver {
    /** Return the supported Smithy {@link software.amazon.smithy.model.shapes.ShapeType} that this resolver will be executed for
     *
     * @return Set of {@code ShapeType} values represented as a string.
     */
    ShapeType getSupportedShapeType();

    ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap);
}

package com.hmellema.sgf4j.resolving;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;


public interface Resolver {
    /** Return the supported Smithy {@link software.amazon.smithy.model.shapes.ShapeType} that this resolver will be executed for
     *
     * Note: only one resolver can be registered per shape type
     *
     * @return {@code ShapeType} that this resolver will be linked to
     */
    ShapeType getSupportedShapeType();

    ShapeGenMetadata resolve(Shape shape, MetaDataLoader metaDataLoader);
}

package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class DoubleResolver implements Resolver {
  private static final ShapeType SUPPORTED_SHAPE_TYPE = ShapeType.DOUBLE;
  private static final TypeName DOUBLE_TYPE_NAME = TypeName.get(Double.class);

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_SHAPE_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new SimpleShapeGenMetadata(shape, DOUBLE_TYPE_NAME);
  }
}

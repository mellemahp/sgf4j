package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.EnumShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class IntEnumResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.INT_ENUM;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new EnumShapeGenMetadata(shape);
  }
}

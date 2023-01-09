package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.traits.BoxTrait;

public class IntegerResolver implements Resolver {
  private static final ShapeType SUPPORTED_SHAPE_TYPE = ShapeType.INTEGER;
  private static final TypeName INTEGER_TYPE_NAME = TypeName.get(Integer.class);

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_SHAPE_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
      return new SimpleShapeGenMetadata(shape, INTEGER_TYPE_NAME);
  }
}

package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import java.security.InvalidParameterException;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class SetTypeResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.SET;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    throw new InvalidParameterException("Set Shape types are deprecated. Use a List type with uniqueItems trait instead.");
  }
}

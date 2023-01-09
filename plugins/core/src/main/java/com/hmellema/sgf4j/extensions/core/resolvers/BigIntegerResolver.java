package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.math.BigInteger;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class BigIntegerResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.BIG_INTEGER;
  private static final TypeName BIG_INT_TYPE_NAME = ClassName.get(BigInteger.class);

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new SimpleShapeGenMetadata(shape, BIG_INT_TYPE_NAME);
  }
}

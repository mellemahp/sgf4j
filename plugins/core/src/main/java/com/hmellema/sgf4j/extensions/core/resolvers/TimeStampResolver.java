package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.TypeName;
import java.util.Date;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class TimeStampResolver implements Resolver {
  private static final ShapeType SUPPORTED_SHAPE_TYPE = ShapeType.TIMESTAMP;
  private static final TypeName TIMESTAMP_TYPE_NAME = TypeName.get(Date.class);

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_SHAPE_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new SimpleShapeGenMetadata(shape, TIMESTAMP_TYPE_NAME);
  }
}

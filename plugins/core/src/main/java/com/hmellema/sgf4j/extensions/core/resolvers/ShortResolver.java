package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.resolving.Resolver;
import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class ShortResolver implements Resolver {
  private static final ShapeType SUPPORTED_SHAPE_TYPE = ShapeType.SHORT;
  private static final TypeName SHORT_TYPE_NAME = TypeName.get(Short.class);

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_SHAPE_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, MetaDataLoader metaDataLoader) {
    return new SimpleShapeGenMetadata(shape, SHORT_TYPE_NAME);
  }
}

package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.UnionShapeGenMetadata;
import com.hmellema.sgf4j.extensions.core.util.TypeConversionUtil;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.resolving.Resolver;
import software.amazon.smithy.model.shapes.*;

public class UnionResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.UNION;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, MetaDataLoader metaDataLoader) {
    var memberData = shape.getAllMembers()
            .values().stream()
            .map(Shape::getId)
            .map(metaDataLoader::resolve).toList();

    return new UnionShapeGenMetadata(shape, TypeConversionUtil.extractStandaloneTypeName(shape), memberData);
  }
}

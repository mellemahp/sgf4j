package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.UnionShapeGenMetadata;
import com.hmellema.sgf4j.extensions.core.util.TypeConversionUtil;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import software.amazon.smithy.model.shapes.*;

import java.util.Set;
import java.util.stream.Collectors;

public class UnionResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.UNION;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    var unionShape = (UnionShape) shape;
    return unionShape.members().stream().map(Shape::getId).collect(Collectors.toSet());
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new UnionShapeGenMetadata(shape, TypeConversionUtil.extractStandaloneTypeName(shape));
  }
}

package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.StructureShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.resolving.Resolver;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.StructureShape;

import java.util.stream.Collectors;

public class StructureResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.STRUCTURE;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }


  @Override
  public ShapeGenMetadata resolve(Shape shape, MetaDataLoader metaDataLoader) {
    var structureShape = (StructureShape) shape;
    var memberData = structureShape.getAllMembers()
            .values().stream()
            .map(Shape::getId)
            .map(metaDataLoader::resolve)
            .toList();

    return new StructureShapeGenMetadata(shape, memberData);
  }
}

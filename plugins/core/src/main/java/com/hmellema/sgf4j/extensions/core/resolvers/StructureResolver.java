package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.StructureShapeGenMetadata;
import com.hmellema.sgf4j.extensions.core.util.TypeConversionUtil;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.StructureShape;

import java.util.Set;
import java.util.stream.Collectors;

public class StructureResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.STRUCTURE;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    var structureShape = (StructureShape) shape;
    return structureShape.members().stream().map(Shape::getId).collect(Collectors.toSet());
  }


  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    var structureShape = (StructureShape) shape;
    var memberData = structureShape.getAllMembers()
            .values().stream()
            .map(Shape::getId)
            .map(shapeGenMetadataMap::get)
            .map(memberDataOptional -> memberDataOptional.orElseThrow(() -> new IllegalArgumentException("Tried to access unresolved shape")))
            .collect(Collectors.toList());

    return new StructureShapeGenMetadata(shape, memberData);
  }
}

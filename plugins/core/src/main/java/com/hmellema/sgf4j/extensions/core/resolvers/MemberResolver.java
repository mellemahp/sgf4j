package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.MemberShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import java.util.Set;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

public class MemberResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.MEMBER;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    return Set.of(((MemberShape) shape).getTarget());
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    // Member types inherit their type from their target
    var memberShape = (MemberShape) shape;
    var target = shapeGenMetadataMap.get(memberShape.getTarget()).orElseThrow(
            () -> new IllegalArgumentException("Tried to access unresolved shape " + memberShape.getTarget())
    );
    return new MemberShapeGenMetadata(shape, target);
  }
}

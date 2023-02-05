package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.MemberShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.resolving.Resolver;
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
  public ShapeGenMetadata resolve(Shape shape, MetaDataLoader metaDataLoader) {
    // Member types inherit their type from their target
    var memberShape = (MemberShape) shape;
    var target = metaDataLoader.resolve(memberShape.getTarget());
    return new MemberShapeGenMetadata(shape, target);
  }
}

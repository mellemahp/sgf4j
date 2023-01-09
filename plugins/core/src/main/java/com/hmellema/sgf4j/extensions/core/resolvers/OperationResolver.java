package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.OperationShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import java.util.HashSet;
import java.util.Set;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

public class OperationResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.OPERATION;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    Set<ShapeId> dependenctShapes = new HashSet<>();
    var operationShape = (OperationShape) shape;
    dependenctShapes.add(operationShape.getInputShape());
    dependenctShapes.add(operationShape.getOutputShape());
    dependenctShapes.addAll(operationShape.getErrors());

    return dependenctShapes;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new OperationShapeGenMetadata(shape);
  }
}

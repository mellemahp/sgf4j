package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.ServiceShapeGenMetadata;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import java.util.HashSet;
import java.util.Set;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

public class ServiceResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.SERVICE;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    var serviceShape = (ServiceShape) shape;
    // Add all independent operations
    Set<ShapeId> dependentShapes = new HashSet<>(serviceShape.getAllOperations());

    // Add all resources
    dependentShapes.addAll(serviceShape.getResources());

    // Add all errors
    dependentShapes.addAll(serviceShape.getErrors());

    return dependentShapes;
  }


  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new ServiceShapeGenMetadata(shape);
  }
}

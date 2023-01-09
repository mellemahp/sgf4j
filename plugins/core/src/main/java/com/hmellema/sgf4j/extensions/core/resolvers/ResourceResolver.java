package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.ResourceShapeGenMetada;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import java.util.HashSet;
import java.util.Set;
import software.amazon.smithy.model.shapes.ResourceShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

public class ResourceResolver implements Resolver {
  private static final ShapeType SUPPORTED_TYPE = ShapeType.RESOURCE;

  @Override
  public ShapeType getSupportedShapeType() {
    return SUPPORTED_TYPE;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    var resourceShape = (ResourceShape) shape;
    // Add all independent operations
    Set<ShapeId> dependentShapes = new HashSet<>(resourceShape.getAllOperations());

    // Add any subresources
    dependentShapes.addAll(resourceShape.getResources());

    return dependentShapes;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    return new ResourceShapeGenMetada(shape);
  }
}

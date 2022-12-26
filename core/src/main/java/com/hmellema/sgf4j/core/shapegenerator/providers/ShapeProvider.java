package com.hmellema.sgf4j.core.shapegenerator.providers;

import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

public interface ShapeProvider {
  Shape getShape();
  ShapeId getShapeId();
  ShapeType getShapeType();
}

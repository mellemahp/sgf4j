package com.hmellema.sgf4j.core.mapping;

import com.hmellema.sgf4j.core.shapegenerator.AbstractShapeGenerator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import software.amazon.smithy.model.shapes.ShapeId;

public class ShapeGeneratorMap {
  Map<ShapeId, AbstractShapeGenerator> shapeGenDataMap = new HashMap<>();

  public void put(ShapeId shapeId, AbstractShapeGenerator shapeGenerator) {
    Objects.requireNonNull(shapeId, "shapeId cannot be null");
    Objects.requireNonNull(shapeGenerator, "shapeGenerator cannot be null");
    shapeGenDataMap.put(shapeId, shapeGenerator);
  }

  public Optional<AbstractShapeGenerator> get(ShapeId shapeId) {
    return Optional.ofNullable(shapeGenDataMap.get(shapeId));
  }

  public Collection<AbstractShapeGenerator> values() {
    return this.shapeGenDataMap.values();
  }

  public Collection<AbstractShapeGenerator> generators() {
    return shapeGenDataMap.values();
  }
}

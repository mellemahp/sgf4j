package com.hmellema.sgf4j.core.processor;

import com.hmellema.sgf4j.core.shapegenerator.AbstractShapeGenerator;
import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import java.util.EnumSet;
import software.amazon.smithy.model.shapes.ShapeType;

public interface Processor {
  EnumSet<ShapeType> getSupportedShapeTypes();
  void process(AbstractShapeGenerator shapeData, ShapeGeneratorMap shapeGeneratorMap);
}

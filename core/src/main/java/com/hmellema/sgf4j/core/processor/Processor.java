package com.hmellema.sgf4j.core.processor;

import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.hmellema.sgf4j.core.shapegenerator.AbstractShapeGenerator;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.EnumSet;

public interface Processor {
    EnumSet<ShapeType> getSupportedShapeTypes();

    void process(AbstractShapeGenerator shapeData, ShapeGeneratorMap shapeGeneratorMap);
}

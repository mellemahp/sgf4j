package com.hmellema.sgf4j.core.resolver;

import com.hmellema.sgf4j.core.shapegenerator.AbstractShapeGenerator;
import com.hmellema.sgf4j.core.typeconversion.TypeConverterMap;
import java.util.EnumSet;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public interface Resolver {
  EnumSet<ShapeType> getSupportedShapeTypes();

  AbstractShapeGenerator process(Shape shape, Model model, TypeConverterMap converterMap);
}

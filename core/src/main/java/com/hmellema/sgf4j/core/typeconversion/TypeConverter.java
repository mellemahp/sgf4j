package com.hmellema.sgf4j.core.typeconversion;

import com.squareup.javapoet.TypeName;
import java.util.EnumSet;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public interface TypeConverter {
  EnumSet<ShapeType> shapeTypes();
  TypeName convert(Shape shape, Model model, TypeConverterMap converterMap);
}

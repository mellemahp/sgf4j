package com.hmellema.sgf4j.core.typeconversion;

import com.squareup.javapoet.TypeName;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class TypeConverterMap {
  public final Map<ShapeType, TypeConverter> shapeConverterMap = new EnumMap<>(ShapeType.class);

  public TypeConverterMap(List<TypeConverter> converters) {
    Objects.requireNonNull(converters, "converters cannot be null.");
    if (converters.size() == 0) {
      throw new IllegalArgumentException("At least one converter must be specified");
    }
    converters.forEach(this::register);
  }

  private void register(TypeConverter converter) {
    for (var type : converter.shapeTypes()) {
      shapeConverterMap.put(type, converter);
    }
    // TODO: log overrides
  }

  public TypeName convert(Shape shape, Model model) {
    var converter = Optional.ofNullable(shapeConverterMap.get(shape.getType()))
        .orElseThrow(() -> new NullPointerException("No converter registered for shape of type " + shape.getType()));
    return converter.convert(shape, model, this);
  }
}

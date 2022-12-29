package com.hmellema.sgf4j.core.typeconversion;

import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.*;

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

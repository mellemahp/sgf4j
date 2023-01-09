package com.hmellema.sgf4j.extensions.core.util;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.shapes.Shape;

public interface TypeConversionUtil {
  static TypeName boxPrimitive(TypeName type) {
    if (type.isPrimitive()) {
      return type.box();
    }
    return type;
  }

  static TypeName extractStandaloneTypeName(Shape shape) {
    return ClassName.get(shape.getId().getNamespace(), shape.getId().getName());
  }
}

package com.hmellema.sgf4j.core.util;

import com.hmellema.sgf4j.core.typeconversion.TypeConverterMap;
import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.MemberShape;

public interface TypeConversionUtil {
  static TypeName boxPrimitive(TypeName type) {
    if (type.isPrimitive()) {
      return type.box();
    }
    return type;
  }

  static TypeName getMemberShapeTargetType(MemberShape shape, Model model, TypeConverterMap converterMap) {
    return model.getShape(shape.getTarget())
        .map(target -> converterMap.convert(target, model))
        .orElseThrow(() -> new IllegalStateException("Target of shape " + shape + " not found"));
  }

}

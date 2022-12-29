package com.hmellema.sgf4j.core.shapegenerator.providers;

import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.squareup.javapoet.ParameterSpec;

public interface ParameterProvider {
    ParameterSpec asParameter(String parameterName, ShapeGeneratorMap shapeGeneratorMap);
}

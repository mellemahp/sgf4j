package com.hmellema.sgf4j.gendata.providers;

import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.ParameterSpec;

public interface ParameterProvider {
    ParameterSpec asParameter(String parameterName, ShapeGenMetadataMap shapeGeneratorMap);
}

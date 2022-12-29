package com.hmellema.sgf4j.core.generator;

import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.squareup.javapoet.JavaFile;

import java.util.Optional;

public interface JavaFileGenerator {
    Optional<JavaFile> generate(ShapeGeneratorMap shapeGeneratorMap);
}

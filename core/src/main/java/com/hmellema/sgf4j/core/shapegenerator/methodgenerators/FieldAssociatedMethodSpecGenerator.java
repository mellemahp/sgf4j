package com.hmellema.sgf4j.core.shapegenerator.methodgenerators;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

@FunctionalInterface
public interface FieldAssociatedMethodSpecGenerator {
  MethodSpec generateMethod(FieldSpec fieldSpec);
}

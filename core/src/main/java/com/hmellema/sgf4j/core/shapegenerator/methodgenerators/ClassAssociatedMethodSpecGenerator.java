package com.hmellema.sgf4j.core.shapegenerator.methodgenerators;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@FunctionalInterface
public interface ClassAssociatedMethodSpecGenerator {
  MethodSpec generateMethod();
}

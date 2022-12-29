package com.hmellema.sgf4j.core.shapegenerator.methodgenerators;

import com.squareup.javapoet.MethodSpec;

@FunctionalInterface
public interface ClassAssociatedMethodSpecGenerator {
    MethodSpec generateMethod();
}

package com.hmellema.sgf4j.gendata.methodgenerators;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

@FunctionalInterface
public interface FieldAssociatedMethodSpecGenerator {
    MethodSpec generateMethod(FieldSpec fieldSpec);
}

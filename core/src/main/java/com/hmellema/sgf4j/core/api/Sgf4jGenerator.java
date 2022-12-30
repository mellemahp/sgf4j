package com.hmellema.sgf4j.core.api;

import com.squareup.javapoet.JavaFile;

import java.util.List;

public interface Sgf4jGenerator {
    /** Generates java files from a model
     *
     * @param request
     * @return
     */
    List<JavaFile> generate(Sgf4jGenerationRequest request);
}

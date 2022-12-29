package com.hmellema.sgf4j.core.generator.executors;

import com.hmellema.sgf4j.core.generator.exceptions.GenerationException;
import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class GenerationExecutor {
    private final Filer filer;

    public GenerationExecutor(Filer filer) {
        this.filer = Objects.requireNonNull(filer, "filer cannot be null");
    }

    public void generate(ShapeGeneratorMap shapeGeneratorMap) {
        shapeGeneratorMap.generators().stream()
                .map(fileGenerator -> fileGenerator.generate(shapeGeneratorMap))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::writeFile);
    }

    private void writeFile(JavaFile generatedFile) {
        try {
            generatedFile.writeTo(filer);
        } catch (IOException e) {
            throw new GenerationException("Code generation failed for Java file" + generatedFile, e);
        }
    }
}

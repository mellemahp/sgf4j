package com.hmellema.sgf4j.core.mapping;

import com.hmellema.sgf4j.core.shapegenerator.AbstractShapeGenerator;
import com.squareup.javapoet.JavaFile;
import software.amazon.smithy.model.shapes.ShapeId;

import java.util.*;

public class ShapeGeneratorMap {
    private final Map<ShapeId, AbstractShapeGenerator> shapeGenDataMap = new HashMap<>();

    public void put(ShapeId shapeId, AbstractShapeGenerator shapeGenerator) {
        Objects.requireNonNull(shapeId, "shapeId cannot be null");
        Objects.requireNonNull(shapeGenerator, "shapeGenerator cannot be null");
        shapeGenDataMap.put(shapeId, shapeGenerator);
    }

    public List<JavaFile> generateFiles() {
        return this.generators().stream()
                .map(fileGenerator -> fileGenerator.generate(this))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Optional<AbstractShapeGenerator> get(ShapeId shapeId) {
        return Optional.ofNullable(shapeGenDataMap.get(shapeId));
    }

    public Collection<AbstractShapeGenerator> values() {
        return this.shapeGenDataMap.values();
    }

    public Collection<AbstractShapeGenerator> generators() {
        return shapeGenDataMap.values();
    }
}

package com.hmellema.sgf4j.core.processor.executor;

import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.hmellema.sgf4j.core.processor.ShapeProcessor;
import com.hmellema.sgf4j.core.processor.TraitProcessor;
import com.hmellema.sgf4j.core.shapegenerator.AbstractShapeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProcessorExecutor {
    private final List<ShapeProcessor> shapeProcessors = new ArrayList<>();
    private final List<TraitProcessor> traitProcessors = new ArrayList<>();

    public ProcessorExecutor(List<ShapeProcessor> shapeProcessors,
                             List<TraitProcessor> traitProcessors
    ) {
        Objects.requireNonNull(shapeProcessors, "shapeProcessors cannot be null.");
        Objects.requireNonNull(traitProcessors, "traitProcessors cannot be null.");

        this.shapeProcessors.addAll(shapeProcessors);
        this.traitProcessors.addAll(traitProcessors);
    }

    public void process(ShapeGeneratorMap shapeGeneratorMap) {
        for (var shapeGenData : shapeGeneratorMap.values()) {
            runShapeProcessors(shapeGenData, shapeGeneratorMap);
            runTraitProcessors(shapeGenData, shapeGeneratorMap);
        }
    }

    private void runTraitProcessors(AbstractShapeGenerator shapeGenerator, ShapeGeneratorMap shapeGeneratorMap) {
        traitProcessors.stream()
                .filter(traitProcessor -> traitProcessor.getSupportedShapeTypes().contains(shapeGenerator.getShapeType()))
                .filter(traitProcessor -> shapeGenerator.getShape().hasTrait(traitProcessor.getTrait()))
                .forEach(traitProcessor -> traitProcessor.process(shapeGenerator, shapeGeneratorMap));
    }

    private void runShapeProcessors(AbstractShapeGenerator shapeGenerator, ShapeGeneratorMap shapeGeneratorMap) {
        shapeProcessors.stream()
                .filter(shapeProcessor -> shapeProcessor.getSupportedShapeTypes().contains(shapeGenerator.getShapeType()))
                .forEach(shapeProcessor -> shapeProcessor.process(shapeGenerator, shapeGeneratorMap));
    }

    public ProcessorExecutor addShapeProcessor(ShapeProcessor shapeProcessor) {
        Objects.requireNonNull(shapeProcessor, "shapeProcessor cannot be null");
        // TODO: do not allow for processors that duplicate supported shapes (via set definition?)
        shapeProcessors.add(shapeProcessor);
        return this;
    }

    public ProcessorExecutor addTraitProcessor(TraitProcessor traitProcessor) {
        Objects.requireNonNull(traitProcessor, "shapeProcessor cannot be null");
        // TODO: do not allow for trait processor that duplicate supported trait->shape combos
        traitProcessors.add(traitProcessor);
        return this;
    }
}

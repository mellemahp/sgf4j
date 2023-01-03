package com.hmellema.sgf4j.mapping;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.*;

public class ShapeGenMetadataMap {
    private final Map<ShapeId, ShapeGenMetadata> shapeGenDataMap = new HashMap<>();

    private final EnumMap<ShapeType, Resolver> resolvers = new EnumMap<>(ShapeType.class);
    private final List<Processor> processors = new ArrayList<>();
    private boolean resolved = false;

    private final Model model;

    public ShapeGenMetadataMap(Model model) {
        Objects.requireNonNull(model, "model cannot be null.");
        this.model = model;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void resolve() {
        // Start at the top of the tree and just let the resolvers
        // walk what is required to define the service
        var service = model.getServiceShapes().stream()
            .reduce((a, b) -> {
                throw new IllegalStateException("Multiple services detected in smithy AST: " + a + ", " + b);
            })
            .orElseThrow(() -> new IllegalStateException("No service found for smithy ast. At least one service is required"));

        // TODO: log the service being used
        getOrCompute(service.getId());
        resolved = true;
    }

    public ShapeGenMetadata resolve(ShapeId shapeId) {
        var shape = model.getShape(shapeId).orElseThrow(
                () -> new IllegalStateException("ShapeId " + shapeId + " could not be found in model")
        );
        var resolver = Optional.ofNullable(resolvers.get(shape.getType())).orElseThrow(
                () -> new IllegalStateException("No resolver registerd for type "+ shape.getType())
        );
        return resolver.resolve(shape, this);
    }

    public ShapeGenMetadata getOrCompute(ShapeId shapeId) {
        var shapeData = shapeGenDataMap.get(shapeId);
        if (shapeData != null) {
            return shapeData;
        }

        shapeData = resolve(shapeId);
        // run all processors on the shape
        executeProcessors(shapeData);
        // save the processed and resolved data to the map
        shapeGenDataMap.put(shapeId, shapeData);

        return shapeData;
    }

    private void executeProcessors(ShapeGenMetadata shapeGenMetadata) {
        processors.stream()
                .filter(processor -> processor.getSupportedShapeTypes().contains(shapeGenMetadata.getShapeType().toString())
                        || processor.getSupportedShapeTypes().contains(Processor.ALL)
                ).filter(processor -> processor.getSupportedShapeTypes().contains(shapeGenMetadata.getShapeType().toString())
                        || processor.getSupportedTraitNames().contains(Processor.ALL)
                ).forEach(processor -> processor.process(shapeGenMetadata));
    }

    public ShapeGenMetadataMap registerAllResolvers(List<Resolver> resolverList) {
        resolverList.forEach(this::registerResolver);
        return this;
    }

    public ShapeGenMetadataMap registerResolver(Resolver resolver) {
        // TODO: add logging if resolver is overridden
        resolvers.put(resolver.getSupportedShapeType(), resolver);
        return this;
    }

    public ShapeGenMetadataMap registerAllProcessors(List<Processor> processorList) {
        processorList.forEach(this::registerProcessor);
        return this;
    }

    public ShapeGenMetadataMap registerProcessor(Processor processor) {
        // TODO: Handle run order of processors?
        processors.add(processor);
        return this;
    }

    public Collection<ShapeGenMetadata> values() {
        return this.shapeGenDataMap.values();
    }
}

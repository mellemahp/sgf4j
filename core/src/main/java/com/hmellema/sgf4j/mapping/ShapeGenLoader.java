package com.hmellema.sgf4j.mapping;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.util.SmithyPreludeLoader;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.*;

public class ShapeGenLoader {
    private static final Model PRELUDE = SmithyPreludeLoader.getPrelude();

    private final EnumMap<ShapeType, Resolver> resolvers = new EnumMap<>(ShapeType.class);
    private final List<Processor> processors = new ArrayList<>();
    private final ShapeGenMetadataMap shapeGenDataMap = new ShapeGenMetadataMap();
    private final Model model;

    public ShapeGenLoader(Model model) {
        Objects.requireNonNull(model, "model cannot be null.");
        // Merge the prelude with the existing model
        this.model = model.toBuilder().addShapes(PRELUDE).build();
    }

    public ShapeGenMetadataMap resolve() {
        resolve(getServiceShape(model));
        return shapeGenDataMap;
    }

    private ShapeId getServiceShape(Model model) {
        // Start at the top of the tree and just let the resolvers
        // walk what is required to define the service
        return model.getServiceShapes().stream()
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple services detected in smithy AST: " + a + ", " + b);
                })
                .map(Shape::getId)
                .orElseThrow(() -> new IllegalStateException("No service found for smithy ast. At least one service is required"));
    }

    public void resolve(ShapeId shapeId) {
        // if the entry does not exists, resolve it
        if (shapeGenDataMap.get(shapeId) == null) {
            // get the shape data from the smithy model
            var shape = model.getShape(shapeId).orElseThrow(
                    () -> new IllegalStateException("ShapeId " + shapeId + " could not be found in model")
            );

            // Determine the resolver to use
            var resolver = Optional.ofNullable(resolvers.get(shape.getType())).orElseThrow(
                    () -> new IllegalStateException("No resolver registered for type "+ shape.getType())
            );

            // Get and resolve all dependencies before attempting to resolve this shape
            resolver.getDependentShapes(shape).forEach(this::resolve);

            // resolve shape now dependencies are resolved
            var shapeMetada = resolver.resolve(shape, shapeGenDataMap);

            // Execute processors on resolved shape
            executeProcessors(shapeMetada);

            // Save resolved shape to the map
            shapeGenDataMap.put(shapeId, shapeMetada);
        }
    }


    public void registerAllResolvers(List<Resolver> resolverList) {
        resolverList.forEach(this::registerResolver);
    }

    public void registerResolver(Resolver resolver) {
        // TODO: add logging if resolver is overridden
        resolvers.put(resolver.getSupportedShapeType(), resolver);
    }

    public void registerAllProcessors(List<Processor> processorList) {
        processorList.forEach(this::registerProcessor);
    }

    public void registerProcessor(Processor processor) {
        // TODO: Handle run order of processors?
        processors.add(processor);
    }

    private void executeProcessors(ShapeGenMetadata shapeGenMetadata) {
        processors.stream()
                .filter(processor -> processor.getSupportedShapeTypes().contains(shapeGenMetadata.getShapeType().toString())
                        || processor.getSupportedShapeTypes().contains(Processor.ALL)
                ).filter(processor -> processor.getSupportedShapeTypes().contains(shapeGenMetadata.getShapeType().toString())
                        || processor.getSupportedTraitNames().contains(Processor.ALL)
                ).forEach(processor -> processor.process(shapeGenMetadata));
    }
}

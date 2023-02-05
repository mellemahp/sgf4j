package com.hmellema.sgf4j.loader;

import com.hmellema.sgf4j.extension.ProcessorExtension;
import com.hmellema.sgf4j.extension.ResolverExtension;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.resolving.ResolverMap;
import com.hmellema.sgf4j.traitprocessing.Processor;
import com.hmellema.sgf4j.util.SmithyPreludeLoader;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MetaDataLoader {
    private static final Model PRELUDE = SmithyPreludeLoader.getPrelude();

    private final List<Processor> processors = new ArrayList<>();
    private final ResolverMap resolverMap = new ResolverMap();
    private final MetadataCache cache = new MetadataCache();
    private final Model model;

    public MetaDataLoader(Model model) {
        Objects.requireNonNull(model, "model cannot be null.");
        this.model = model.toBuilder().addShapes(PRELUDE).build();
    }

    public Set<ShapeGenMetadata> resolve(Set<ShapeId> serviceClosure) {
        return serviceClosure.stream()
                .map(this::resolve)
                .collect(Collectors.toSet());
    }

    public ShapeGenMetadata resolve(ShapeId shapeId) {
        // check if the metadata has already been resolved
        var shapeMetadataOptional = cache.get(shapeId);
        if (shapeMetadataOptional.isPresent()) {
            return shapeMetadataOptional.get();
        }

        // Resolver shape as it does not already exist
        var shape = getShape(shapeId);
        var resolver = resolverMap.get(shape);
        var shapeMetadata = resolver.resolve(shape, this);
        process(shapeMetadata);
        cache.put(shapeId, shapeMetadata);

        return shapeMetadata;
    }

    private Shape getShape(ShapeId shapeId) {
        return model.getShape(shapeId).orElseThrow(
                () -> new IllegalStateException("ShapeId " + shapeId + " could not be found in model")
        );
    }

    private void process(ShapeGenMetadata shapeGenMetadata) {
        processors.stream()
                .filter(processor -> processor.shouldExecute(shapeGenMetadata))
                .forEach(processor -> processor.process(shapeGenMetadata, this));
    }

    public void register(ResolverExtension resolverExtension) {
        // TODO: log when a resolver is overwritten
        Objects.requireNonNull(resolverExtension, "resolverExtension cannot be null.");
        resolverMap.putAll(resolverExtension.getResolvers());
    }

    public void register(ProcessorExtension processorExtension) {
        Objects.requireNonNull(processorExtension, "processorExtension cannot be null.");
        processors.addAll(processorExtension.getProcessors());
    }
}

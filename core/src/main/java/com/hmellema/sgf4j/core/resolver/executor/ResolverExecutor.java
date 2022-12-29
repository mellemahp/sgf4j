package com.hmellema.sgf4j.core.resolver.executor;

import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.hmellema.sgf4j.core.resolver.Resolver;
import com.hmellema.sgf4j.core.typeconversion.TypeConverterMap;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ResolverExecutor {
    private final List<Resolver> resolvers = new ArrayList<>();
    private final List<String> namespaceFilters = new ArrayList<>();

    private final TypeConverterMap typeConverterMap;

    public ResolverExecutor(List<Resolver> resolvers, List<String> namespaceFilters, TypeConverterMap typeConverterMap) {
        Objects.requireNonNull(resolvers, "resolvers cannot be null.");
        Objects.requireNonNull(namespaceFilters, "namespacefilters cannot be null.");
        if (resolvers.size() == 0) {
            throw new IllegalArgumentException("At least one resolver must be specified");
        }
        this.resolvers.addAll(resolvers);
        this.namespaceFilters.addAll(namespaceFilters);
        this.typeConverterMap = Objects.requireNonNull(typeConverterMap, "typeConverterMap cannot be null.");
    }

    public ShapeGeneratorMap resolveShapeMap(Model model) {
        Objects.requireNonNull(model, "model must not be null");
        var shapeGenDataMap = new ShapeGeneratorMap();

        model.shapes()
                .filter(shape -> namespaceFilters.stream().noneMatch(filt -> filt.equals(shape.getId().getNamespace())))
                .forEach(shape -> {
                    var initializerOptional = getResolverForShape(shape);
                    if (initializerOptional.isPresent()) {
                        var initializer = initializerOptional.get();
                        shapeGenDataMap.put(shape.getId(), initializer.process(shape, model, typeConverterMap));
                    }
                });

        return shapeGenDataMap;
    }

    private Optional<Resolver> getResolverForShape(Shape shape) {
        return resolvers.stream()
                .filter(shapeInitializer -> shapeInitializer.getSupportedShapeTypes().contains(shape.getType()))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple initializers for the same shape: " + a + ", " + b);
                }).stream().findFirst();
        //.orElseThrow(() -> new IllegalStateException("No initializer found for shape: " + shape));
    }

    public ResolverExecutor addShapeResolver(Resolver shapeInitializer) {
        Objects.requireNonNull(shapeInitializer, "shapeInitializer cannot be null");
        // TODO: do not allow for trait processor that duplicate supported trait->shape combos
        resolvers.add(shapeInitializer);
        return this;
    }

    public ResolverExecutor addNamespacefilter(String namespacefilter) {
        Objects.requireNonNull(namespacefilter, "namespacefilter cannot be null");
        // TODO: do not allow for trait processor that duplicate supported trait->shape combos
        namespaceFilters.add(namespacefilter);
        return this;
    }
}

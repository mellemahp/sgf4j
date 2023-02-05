package com.hmellema.sgf4j.resolving;

import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.*;

public class ResolverMap extends EnumMap<ShapeType, Resolver> {
    private static final Class<ShapeType> KEY_TYPE = ShapeType.class;

    public ResolverMap() {
        super(KEY_TYPE);
    }

    public void putAll(Collection<Resolver> resolvers) {
        resolvers.stream().filter(Objects::nonNull).forEach(this::put);
    }

    public void put(Resolver resolver) {
        Objects.requireNonNull(resolver, "resolver cannot be null.");
        put(resolver.getSupportedShapeType(), resolver);
    }

    public Resolver get(Shape shape) {
        return Optional.ofNullable(get(shape.getType())).orElseThrow(
                () -> new IllegalStateException("No resolver registered for type "+ shape.getType())
        );
    }
}

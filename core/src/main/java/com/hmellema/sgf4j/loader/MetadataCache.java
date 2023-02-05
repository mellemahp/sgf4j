package com.hmellema.sgf4j.loader;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import software.amazon.smithy.model.shapes.ShapeId;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MetadataCache {
    private final Map<ShapeId, ShapeGenMetadata> shapeGenMetadataMap = new HashMap<>();

    public void put(ShapeId shapeId, ShapeGenMetadata shapeGenMetadata) {
        Objects.requireNonNull(shapeGenMetadata, "shapeGenMetadata cannot be null.");
        Objects.requireNonNull(shapeId, "shapeId cannot be null.");
        Optional.ofNullable(shapeGenMetadataMap.put(shapeId, shapeGenMetadata))
                .ifPresent(value -> {
                    throw new IllegalArgumentException("Shape " + shapeId + "has already be set");
                });
    }

    public Optional<ShapeGenMetadata> get(ShapeId shapeId) {
        return Optional.ofNullable(shapeGenMetadataMap.get(shapeId));
    }
}

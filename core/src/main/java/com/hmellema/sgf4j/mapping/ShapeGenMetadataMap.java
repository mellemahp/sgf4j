package com.hmellema.sgf4j.mapping;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import software.amazon.smithy.model.shapes.ShapeId;

import java.util.*;

public class ShapeGenMetadataMap {
    private final Map<ShapeId, ShapeGenMetadata> shapeGenDataMap = new HashMap<>();

    public Optional<ShapeGenMetadata> get(ShapeId shapeId) {
        return Optional.ofNullable(shapeGenDataMap.get(shapeId));
    }

    void put(ShapeId shapeId, ShapeGenMetadata metadata) {
        Optional.ofNullable(shapeGenDataMap.put(shapeId, metadata)).ifPresent(existingShape -> {
            throw new IllegalStateException("Existing shape " + existingShape + " was overwritten during resolution. This is not permitted.");
        });
    }

    public Collection<ShapeGenMetadata> values() {
        return this.shapeGenDataMap.values();
    }
}

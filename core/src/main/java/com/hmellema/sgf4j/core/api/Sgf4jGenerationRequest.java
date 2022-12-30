package com.hmellema.sgf4j.core.api;

import software.amazon.smithy.model.Model;

import java.util.List;
import java.util.Objects;

public record Sgf4jGenerationRequest(Model model, List<String> namespaceFilters) {
    public Sgf4jGenerationRequest {
        Objects.requireNonNull(model, "smithyModelPath cannot be null");
        Objects.requireNonNull(namespaceFilters, "namespaceFilters cannot be null");
    }
}

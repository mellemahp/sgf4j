package com.hmellema.sgf4j.api;

import software.amazon.smithy.model.Model;

import java.util.List;
import java.util.Objects;

public record Sgf4jGenerationRequest(Model model, ClassLoader classLoader, List<String> namespaceFilters) {
    public Sgf4jGenerationRequest {
        Objects.requireNonNull(model, "model cannot be null");
        Objects.requireNonNull(classLoader, "classLoader cannot be null");
        Objects.requireNonNull(namespaceFilters, "namespaceFilters cannot be null");
    }
}

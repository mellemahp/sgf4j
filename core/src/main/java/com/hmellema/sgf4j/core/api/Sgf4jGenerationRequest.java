package com.hmellema.sgf4j.core.api;

import javax.annotation.processing.Filer;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public record Sgf4jGenerationRequest(URL smithyModelPath,
                                     Filer filer,
                                     List<String> namespaceFilters
) {
    public Sgf4jGenerationRequest {
        Objects.requireNonNull(smithyModelPath, "smithyModelPath cannot be null");
        Objects.requireNonNull(filer, "filer cannot be null.");
        Objects.requireNonNull(namespaceFilters, "namespaceFilters cannot be null");
    }
}

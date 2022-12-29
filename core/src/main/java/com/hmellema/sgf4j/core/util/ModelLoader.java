package com.hmellema.sgf4j.core.util;

import com.hmellema.sgf4j.core.api.Sgf4jGenerationRequest;
import software.amazon.smithy.model.Model;

public interface ModelLoader {
    static Model load(Sgf4jGenerationRequest request) {
        return Model.assembler().addImport(request.smithyModelPath()).assemble().unwrap();
    }
}

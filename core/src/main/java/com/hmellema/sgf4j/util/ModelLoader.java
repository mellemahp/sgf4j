package com.hmellema.sgf4j.util;

import software.amazon.smithy.model.Model;

import java.net.URL;

public interface ModelLoader {
    static Model load(URL modelURL) {
        return Model.assembler().addImport(modelURL).assemble().unwrap();
    }
}

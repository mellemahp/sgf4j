package com.hmellema.sgf4j.gendata.providers;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public interface JavaFileProvider {
    default Map<Class<?>, String> getStaticImports() {
        return Collections.emptyMap();
    }
    default void putStaticImport(Class<?> clazz, String str) {
        throw new UnsupportedOperationException("putStaticImport not defined for this metadata object");
    }

    default Optional<String> getFileTest() {
        return Optional.empty();
    }

    default void setFileComment(String fileComment) {
        throw new UnsupportedOperationException("setFileComment not defined for this metadata object");
    }
}

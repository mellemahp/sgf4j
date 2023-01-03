package com.hmellema.sgf4j.gendata.providers;

import java.util.Map;
import java.util.Optional;

public interface JavaFileProvider {
    Map<Class<?>, String> getStaticImports();
    void putStaticImport(Class<?> clazz, String str);

    Optional<String> getFileComment();
    void setFileComment(String fileComment);
}

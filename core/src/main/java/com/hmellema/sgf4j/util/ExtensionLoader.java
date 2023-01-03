package com.hmellema.sgf4j.util;

import com.hmellema.sgf4j.extension.CodeGenExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface ExtensionLoader {
    static List<CodeGenExtension> load(ClassLoader classLoader) {
        List<CodeGenExtension> extensions = new ArrayList<>();
        // TODO: Maybe list the name of the extension as it is loaded?
        for (var extension : ServiceLoader.load(CodeGenExtension.class, classLoader)) {
            extensions.add(extension);
        }
        return extensions;
    }
}

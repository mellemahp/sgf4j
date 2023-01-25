package com.hmellema.sgf4j.util;

import com.hmellema.sgf4j.extension.CodeGenExtension;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

import java.util.List;

public interface ExtensionLoader {
    static List<CodeGenExtension> load() {
        PluginManager pluginManager = new DefaultPluginManager();

        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        var extensions = pluginManager.getExtensions(CodeGenExtension.class);
        pluginManager.stopPlugins();
        return extensions;
    }
}

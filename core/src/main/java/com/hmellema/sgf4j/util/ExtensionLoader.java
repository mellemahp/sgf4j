package com.hmellema.sgf4j.util;

import com.hmellema.sgf4j.extension.ProcessorExtension;
import com.hmellema.sgf4j.extension.ResolverExtension;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

import java.util.List;

public interface ExtensionLoader {
    PluginManager PLUGIN_MANAGER = new DefaultPluginManager();

    /** Loads all metadata resolver plugins
     *
     * @return list of loaded metadata resolver plugins loaded
     */
    static List<ResolverExtension> loadResolverExtensions() {
        PLUGIN_MANAGER.loadPlugins();
        return PLUGIN_MANAGER.getExtensions(ResolverExtension.class);
    }

    /** Loads all processors extension plugins
     *
     * @return List of processor extensions that were loaded
     */
    static List<ProcessorExtension> loadProcessorExtensions() {
        PLUGIN_MANAGER.loadPlugins();
        return PLUGIN_MANAGER.getExtensions(ProcessorExtension.class);
    }
}

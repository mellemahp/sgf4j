package com.hmellema.sgf4j.core;

import com.hmellema.sgf4j.core.api.Sgf4jGenerationRequest;
import com.hmellema.sgf4j.core.api.Sgf4jGenerator;
import com.hmellema.sgf4j.core.plugin.extensionpoints.ResolverExtensionPoint;
import com.hmellema.sgf4j.core.plugin.extensionpoints.ShapeProcessorExtensionPoint;
import com.hmellema.sgf4j.core.plugin.extensionpoints.TraitProcessorExtensionPoint;
import com.hmellema.sgf4j.core.plugin.extensionpoints.TypeConverterExtensionPoint;
import com.hmellema.sgf4j.core.processor.ShapeProcessor;
import com.hmellema.sgf4j.core.processor.TraitProcessor;
import com.hmellema.sgf4j.core.processor.executor.ProcessorExecutor;
import com.hmellema.sgf4j.core.resolver.Resolver;
import com.hmellema.sgf4j.core.resolver.executor.ResolverExecutor;
import com.hmellema.sgf4j.core.typeconversion.TypeConverter;
import com.hmellema.sgf4j.core.typeconversion.TypeConverterMap;
import com.squareup.javapoet.JavaFile;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class DefaultCodeGenerator implements Sgf4jGenerator {
    private final PluginManager pluginManager = new DefaultPluginManager();

    private final List<TypeConverter> converters = new ArrayList<>();
    private final List<Resolver> resolvers = new ArrayList<>();
    private final List<ShapeProcessor> shapeProcessors = new ArrayList<>();
    private final List<TraitProcessor> traitProcessors = new ArrayList<>();

    @Override
    public List<JavaFile> generate(Sgf4jGenerationRequest request) {
        // load plugins
        initExtensions();

        // set up all exectuors
        var model = request.model();
        var converterMap = new TypeConverterMap(converters);
        var resolver = new ResolverExecutor(resolvers, request.namespaceFilters(), converterMap);
        var processor = new ProcessorExecutor(shapeProcessors, traitProcessors);

        // Execute initializer
        var mapping = resolver.resolveShapeMap(model);
        processor.process(mapping);
        return mapping.generateFiles();
    }


    private void initExtensions() {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        converters.addAll(pluginManager.getExtensions(TypeConverterExtensionPoint.class));
        resolvers.addAll(pluginManager.getExtensions(ResolverExtensionPoint.class));
        shapeProcessors.addAll(pluginManager.getExtensions(ShapeProcessorExtensionPoint.class));
        traitProcessors.addAll(pluginManager.getExtensions(TraitProcessorExtensionPoint.class));
        pluginManager.stopPlugins();
    }
}

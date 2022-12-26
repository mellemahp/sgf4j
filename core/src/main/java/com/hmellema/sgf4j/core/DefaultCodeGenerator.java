package com.hmellema.sgf4j.core;

import com.hmellema.sgf4j.core.api.Sgf4jGenerationRequest;
import com.hmellema.sgf4j.core.api.Sgf4jGenerator;
import com.hmellema.sgf4j.core.generator.executors.GenerationExecutor;
import com.hmellema.sgf4j.core.plugin.extensionpoints.TypeConverterExtensionPoint;
import com.hmellema.sgf4j.core.processor.executor.ProcessorExecutor;
import com.hmellema.sgf4j.core.resolver.executor.ResolverExecutor;

import com.hmellema.sgf4j.core.plugin.extensionpoints.ResolverExtensionPoint;
import com.hmellema.sgf4j.core.plugin.extensionpoints.ShapeProcessorExtensionPoint;
import com.hmellema.sgf4j.core.plugin.extensionpoints.TraitProcessorExtensionPoint;
import com.hmellema.sgf4j.core.processor.ShapeProcessor;
import com.hmellema.sgf4j.core.processor.TraitProcessor;
import com.hmellema.sgf4j.core.resolver.Resolver;

import com.hmellema.sgf4j.core.typeconversion.TypeConverter;
import com.hmellema.sgf4j.core.typeconversion.TypeConverterMap;
import com.hmellema.sgf4j.core.util.ModelLoader;
import java.util.List;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

public class DefaultCodeGenerator implements Sgf4jGenerator {
  private final PluginManager pluginManager = new DefaultPluginManager();

  private List<TypeConverter> converters;
  private List<Resolver> resolvers;
  private List<ShapeProcessor> shapeProcessors;
  private List<TraitProcessor> traitProcessors;

  @Override
  public void generate(Sgf4jGenerationRequest request) {
    // load plugins
    initExtensions();

    // set up all exectuors
    var model = ModelLoader.load(request);
    var converterMap = new TypeConverterMap(converters);
    var resolver = new ResolverExecutor(resolvers, request.namespaceFilters(), converterMap);
    var processor = new ProcessorExecutor(shapeProcessors, traitProcessors);
    var generator = new GenerationExecutor(request.filer());

    // Execute initializer
    var mapping = resolver.resolveShapeMap(model);
    processor.process(mapping);
    generator.generate(mapping);
  }

  private void initExtensions() {
    pluginManager.loadPlugins();
    pluginManager.startPlugins();
    converters = pluginManager.getExtensions(TypeConverterExtensionPoint.class)
        .stream().map(TypeConverter.class::cast).toList();
    resolvers = pluginManager.getExtensions(ResolverExtensionPoint.class)
        .stream().map(Resolver.class::cast).toList();
    shapeProcessors = pluginManager.getExtensions(ShapeProcessorExtensionPoint.class)
        .stream().map(ShapeProcessor.class::cast).toList();
    traitProcessors = pluginManager.getExtensions(TraitProcessorExtensionPoint.class)
        .stream().map(TraitProcessor.class::cast).toList();
    pluginManager.stopPlugins();
  }
}

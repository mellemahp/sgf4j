package com.hmellema.sgf4j.extensions.jackson;

import com.hmellema.sgf4j.extension.CodeGenExtension;
import com.hmellema.sgf4j.extensions.jackson.processors.DeserializationOrderProcessor;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.Resolver;
import org.pf4j.Extension;

import java.util.Collections;
import java.util.List;

@Extension
public class JacksonCodeGenExtension implements CodeGenExtension {
    private static final String PLUGIN_NAME = "jackson-plugin";
    private static final List<Processor> PROCESSOR_LIST = List.of(
            new DeserializationOrderProcessor()
    );

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public List<Resolver> getResolvers() {
        return Collections.emptyList();
    }

    @Override
    public List<Processor> getProcessors() {
        return PROCESSOR_LIST;
    }
}
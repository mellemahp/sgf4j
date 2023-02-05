package com.hmellema.sgf4j.extensions.jackson;

import com.hmellema.sgf4j.extension.ProcessorExtension;
import com.hmellema.sgf4j.extensions.jackson.processors.DeserializationOrderProcessor;
import com.hmellema.sgf4j.traitprocessing.Processor;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class JacksonCodeGenExtension implements ProcessorExtension {
    private static final String PLUGIN_NAME = "jackson-plugin";
    private static final List<Processor> PROCESSOR_LIST = List.of(
            new DeserializationOrderProcessor()
    );

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public List<Processor> getProcessors() {
        return PROCESSOR_LIST;
    }
}
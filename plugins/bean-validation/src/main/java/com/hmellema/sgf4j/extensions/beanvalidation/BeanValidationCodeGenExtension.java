package com.hmellema.sgf4j.extensions.beanvalidation;

import com.hmellema.sgf4j.extension.ProcessorExtension;
import com.hmellema.sgf4j.extensions.beanvalidation.processors.LengthTraitProcessor;
import com.hmellema.sgf4j.extensions.beanvalidation.processors.PatternTraitProcessor;
import com.hmellema.sgf4j.extensions.beanvalidation.processors.RequiredTraitProcessor;
import com.hmellema.sgf4j.traitprocessing.Processor;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class BeanValidationCodeGenExtension implements ProcessorExtension {
    private static final String PLUGIN_NAME = "bean-validation";

    private static final List<Processor> PROCESSOR_LIST = List.of(
            new LengthTraitProcessor(),
            new PatternTraitProcessor(),
            new RequiredTraitProcessor()
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
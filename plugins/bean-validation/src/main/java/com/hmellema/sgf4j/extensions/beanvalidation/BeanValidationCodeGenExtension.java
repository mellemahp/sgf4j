package com.hmellema.sgf4j.extensions.beanvalidation;

import com.hmellema.sgf4j.extension.CodeGenExtension;
import com.hmellema.sgf4j.extensions.beanvalidation.processors.LengthTraitProcessor;
import com.hmellema.sgf4j.extensions.beanvalidation.processors.PatternTraitProcessor;
import com.hmellema.sgf4j.extensions.beanvalidation.processors.RequiredTraitProcessor;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.Resolver;
import org.pf4j.Extension;

import java.util.Collections;
import java.util.List;

@Extension
public class BeanValidationCodeGenExtension implements CodeGenExtension {
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
    public List<Resolver> getResolvers() {
        return Collections.emptyList();
    }

    @Override
    public List<Processor> getProcessors() {
        return PROCESSOR_LIST;
    }
}
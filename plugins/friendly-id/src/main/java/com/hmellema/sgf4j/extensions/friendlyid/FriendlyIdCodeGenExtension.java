package com.hmellema.sgf4j.extensions.friendlyid;

import com.hmellema.sgf4j.extension.CodeGenExtension;
import com.hmellema.sgf4j.extensions.friendlyid.processors.FriendlyIdProcessor;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.Resolver;
import org.pf4j.Extension;

import java.util.Collections;
import java.util.List;

@Extension
public class FriendlyIdCodeGenExtension  implements CodeGenExtension  {
    private static final String EXTENSION_NAME = "Friendly-Id";

    private static final List<Processor> PROCESSOR_LIST = List.of(
            new FriendlyIdProcessor()
    );

    @Override
    public String getName() {
        return EXTENSION_NAME;
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

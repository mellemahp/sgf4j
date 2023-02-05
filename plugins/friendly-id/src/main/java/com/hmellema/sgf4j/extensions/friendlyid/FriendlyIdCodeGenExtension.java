package com.hmellema.sgf4j.extensions.friendlyid;

import com.hmellema.sgf4j.extension.ProcessorExtension;
import com.hmellema.sgf4j.extensions.friendlyid.processors.FriendlyIdProcessor;
import com.hmellema.sgf4j.traitprocessing.Processor;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class FriendlyIdCodeGenExtension  implements ProcessorExtension {
    private static final String EXTENSION_NAME = "Friendly-Id";

    private static final List<Processor> PROCESSOR_LIST = List.of(
            new FriendlyIdProcessor()
    );

    @Override
    public String getName() {
        return EXTENSION_NAME;
    }

    @Override
    public List<Processor> getProcessors() {
        return PROCESSOR_LIST;
    }
}

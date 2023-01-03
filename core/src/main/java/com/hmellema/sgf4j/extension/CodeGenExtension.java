package com.hmellema.sgf4j.extension;


import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.Resolver;

import java.util.List;

public interface CodeGenExtension {
    /** Unique name to assign to the extension
     *
     * @return Extension name
     */
    String getName();

    /**
     *
     * @return
     */
    List<Resolver> getResolvers();
    List<Processor> getProcessors();
}

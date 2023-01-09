package com.hmellema.sgf4j.extension;


import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.Resolver;

import java.util.List;

import org.pf4j.ExtensionPoint;

public interface CodeGenExtension extends ExtensionPoint {
    /** Unique name to assign to the extension
     *
     * @return Extension name
     */
    String getName();

    List<Resolver> getResolvers();
    List<Processor> getProcessors();
}

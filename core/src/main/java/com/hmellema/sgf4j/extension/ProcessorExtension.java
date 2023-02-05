package com.hmellema.sgf4j.extension;

import com.hmellema.sgf4j.traitprocessing.Processor;
import org.pf4j.ExtensionPoint;

import java.util.Collection;

public interface ProcessorExtension extends ExtensionPoint {
    /** Unique name to assign to the extension
     *
     * @return Extension name
     */
    String getName();

    /** Resolvers that this extension will register
     *
     * @return collection of processors
     */
    Collection<Processor> getProcessors();
}

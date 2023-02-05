package com.hmellema.sgf4j.extension;

import com.hmellema.sgf4j.resolving.Resolver;

import java.util.Collection;

import org.pf4j.ExtensionPoint;

public interface ResolverExtension extends ExtensionPoint {
    /** Unique name to assign to the extension
     *
     * @return Extension name
     */
    String getName();

    /** Resolvers that this extension will register
     *
     * @return collection of resolvers
     */
    Collection<Resolver> getResolvers();
}

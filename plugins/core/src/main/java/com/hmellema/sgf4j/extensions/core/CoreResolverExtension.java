package com.hmellema.sgf4j.extensions.core;

import com.hmellema.sgf4j.extension.ResolverExtension;
import com.hmellema.sgf4j.extensions.core.resolvers.*;
import com.hmellema.sgf4j.resolving.Resolver;
import org.pf4j.Extension;

import java.util.Collection;
import java.util.List;

@Extension
public class CoreResolverExtension implements ResolverExtension {
    private static final String EXTENSION_NAME = "core";

    private static final List<Resolver> RESOLVER_LIST= List.of(
            new BigDecimalResolver(),
            new BigIntegerResolver(),
            new BlobResolver(),
            new BooleanResolver(),
            new ByteResolver(),
            new DocumentResolver(),
            new DoubleResolver(),
            new EnumResolver(),
            new IntEnumResolver(),
            new FloatResolver(),
            new IntegerResolver(),
            new ListResolver(),
            new MapResolver(),
            new MemberResolver(),
            new OperationResolver(),
            new ResourceResolver(),
            new ServiceResolver(),
            new StructureResolver(),
            new StringResolver(),
            new TimeStampResolver(),
            new UnionResolver()
    );

    @Override
    public String getName() {
        return EXTENSION_NAME;
    }

    @Override
    public Collection<Resolver> getResolvers() {
        return RESOLVER_LIST;
    }
}

package com.hmellema.sgf4j.extensions.core;

import com.google.auto.service.AutoService;
import com.hmellema.sgf4j.extension.CodeGenExtension;
import com.hmellema.sgf4j.extensions.core.resolvers.BigDecimalResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.BigIntegerResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.BlobResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.BooleanResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.ByteResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.DocumentResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.DoubleResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.EnumResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.FloatResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.IntEnumResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.IntegerResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.ListResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.MapResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.MemberResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.OperationResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.ResourceResolver;
import com.hmellema.sgf4j.extensions.core.resolvers.ServiceResolver;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.Resolver;
import java.util.List;

@AutoService(CodeGenExtension.class)
public class CoreCodeGenExtension implements CodeGenExtension {
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
      new ServiceResolver()
  );

  @Override
  public String getName() {
    return EXTENSION_NAME;
  }

  @Override
  public List<Resolver> getResolvers() {
    return RESOLVER_LIST;
  }

  @Override
  public List<Processor> getProcessors() {
    return null;
  }
}

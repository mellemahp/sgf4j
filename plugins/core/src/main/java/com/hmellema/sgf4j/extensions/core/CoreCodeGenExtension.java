package com.hmellema.sgf4j.extensions.core;

import com.hmellema.sgf4j.extension.CodeGenExtension;
import com.hmellema.sgf4j.extensions.core.processors.*;
import com.hmellema.sgf4j.extensions.core.resolvers.*;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.Resolver;
import org.pf4j.Extension;

import java.util.List;

@Extension
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
      new ServiceResolver(),
      new StructureResolver(),
      new StringResolver(),
      new TimeStampResolver(),
      new UnionResolver()
  );

  private static final List<Processor> PROCESSOR_LIST = List.of(
      new ErrorStructureProcessor(),
      new ConstructorBuilderStructureProcessor(),
      new GetterStructureProcessor(),
      new GeneratedAnnotationProcessor(),
      new ToStringStructureProcessor()
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
  public List<Processor> getProcessors() { return PROCESSOR_LIST; }
}

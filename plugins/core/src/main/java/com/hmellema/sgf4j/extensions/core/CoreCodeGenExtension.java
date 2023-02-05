package com.hmellema.sgf4j.extensions.core;

import com.hmellema.sgf4j.extension.ProcessorExtension;
import com.hmellema.sgf4j.extensions.core.processors.*;
import com.hmellema.sgf4j.traitprocessing.Processor;
import org.pf4j.Extension;

import java.util.List;

@Extension
public class CoreCodeGenExtension implements ProcessorExtension {
  private static final String EXTENSION_NAME = "core";

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
  public List<Processor> getProcessors() { return PROCESSOR_LIST; }
}

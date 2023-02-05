package com.hmellema.sgf4j.extensions.beanvalidation.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.traitprocessing.Processor;
import com.squareup.javapoet.AnnotationSpec;
import jakarta.validation.constraints.NotNull;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.EnumSet;
import java.util.Set;

public class RequiredTraitProcessor implements Processor {
  private static final Set<String> SUPPORTED_TRAITS = Set.of("smithy.api#required");
  // TODO: expand this set
  private static final EnumSet<ShapeType> SUPPORTED_SHAPES =
      EnumSet.of(
          ShapeType.DOUBLE,
          ShapeType.BYTE,
          ShapeType.BIG_DECIMAL,
          ShapeType.BIG_INTEGER,
          ShapeType.BLOB,
          ShapeType.BOOLEAN,
          ShapeType.FLOAT,
          ShapeType.INTEGER,
          ShapeType.LONG,
          ShapeType.SHORT,
          ShapeType.TIMESTAMP,
          ShapeType.STRING,
          ShapeType.MAP,
          ShapeType.STRUCTURE,
          ShapeType.MEMBER);


  @Override
  public EnumSet<ShapeType> getSupportedShapeTypes() {
    return SUPPORTED_SHAPES;
  }

  @Override
  public Set<String> getSupportedTraitNames() { return SUPPORTED_TRAITS; }

  @Override
  public void process(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader) {
    shapeGenMetadata.addFieldAnnotation(AnnotationSpec.builder(NotNull.class).build());
  }
}

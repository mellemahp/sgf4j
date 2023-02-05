package com.hmellema.sgf4j.extensions.beanvalidation.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.traitprocessing.Processor;
import com.squareup.javapoet.AnnotationSpec;
import jakarta.validation.constraints.Pattern;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.traits.PatternTrait;

import java.util.EnumSet;
import java.util.Set;

public class PatternTraitProcessor implements Processor {
  private static final EnumSet<ShapeType> SUPPORTED_TYPES = EnumSet.of(ShapeType.STRING, ShapeType.MEMBER);
  private static final Set<String> SUPPORTED_TRAITS = Set.of("smithy.api#pattern");

  @Override
  public EnumSet<ShapeType> getSupportedShapeTypes() {
    return SUPPORTED_TYPES;
  }

  @Override
  public Set<String> getSupportedTraitNames() { return SUPPORTED_TRAITS; }

  @Override
  public void process(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader) {
    PatternTrait trait = shapeGenMetadata.getShape()
            .getTrait(PatternTrait.class)
            .orElseThrow(() -> new IllegalStateException("attempted to run processor on shape without supported trait."));
    shapeGenMetadata.addFieldAnnotation(
            AnnotationSpec.builder(Pattern.class)
                    .addMember("regexp", "$S", trait.getPattern().toString())
                    .build());
  }
}

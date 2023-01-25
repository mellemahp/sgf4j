package com.hmellema.sgf4j.extensions.beanvalidation.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.AnnotationSpec;
import jakarta.validation.constraints.Size;
import org.pf4j.Extension;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.traits.LengthTrait;

import java.util.EnumSet;
import java.util.Set;

public class LengthTraitProcessor implements Processor {
  private static final EnumSet<ShapeType> SUPPORTED_TYPES = EnumSet.of(ShapeType.STRING, ShapeType.MEMBER);
  private static final Set<String> SUPPORTED_TRAITS = Set.of("smithy.api#length");

  @Override
  public EnumSet<ShapeType> getSupportedShapeTypes() {
    return SUPPORTED_TYPES;
  }

  @Override
  public Set<String> getSupportedTraitNames() {
    return SUPPORTED_TRAITS;
  }

  @Override
  public void process(ShapeGenMetadata shapeGenMetadata, ShapeGenMetadataMap shapeGenMetadataMap) {
    LengthTrait trait = shapeGenMetadata.getShape()
            .getTrait(LengthTrait.class)
            .orElseThrow(() -> new IllegalStateException("attempted to run processor on shape without supported trait."));

    var specBuilder = AnnotationSpec.builder(Size.class);
    trait.getMin().ifPresent(minimum -> specBuilder.addMember("min", "$L", minimum));
    trait.getMax().ifPresent(maximum -> specBuilder.addMember("max", "$L", maximum));

    shapeGenMetadata.addFieldAnnotation(specBuilder.build());
  }
}

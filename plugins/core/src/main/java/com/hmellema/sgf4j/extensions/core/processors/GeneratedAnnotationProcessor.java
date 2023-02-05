package com.hmellema.sgf4j.extensions.core.processors;

import com.hmellema.sgf4j.api.Sgf4jGenerator;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.traitprocessing.Processor;
import com.squareup.javapoet.AnnotationSpec;
import software.amazon.smithy.model.shapes.ShapeType;

import javax.annotation.processing.Generated;
import java.util.EnumSet;
import java.util.Set;

public class GeneratedAnnotationProcessor implements Processor {
    private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.allOf(ShapeType.class);

    @Override
    public EnumSet<ShapeType> getSupportedShapeTypes() {
        return SUPPORTED_SHAPES;
    }

    @Override
    public Set<String> getSupportedTraitNames() {
        return Set.of("*");
    }

    @Override
    public void process(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader) {
        var generatedAnnotation = AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", Sgf4jGenerator.class.getName())
                .build();
        try {
            shapeGenMetadata.addClassAnnotation(generatedAnnotation);
        } catch (UnsupportedOperationException exc) {
            // Just keep going if the particular shape doesn't support the annotation
            // This way it is added wherever it can be
        }
    }
}

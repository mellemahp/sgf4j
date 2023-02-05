package com.hmellema.sgf4j.extensions.friendlyid.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.traitprocessing.Processor;
import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

public class FriendlyIdProcessor implements Processor {
    private static final TypeName UUID_TYPE_NAME = TypeName.get(UUID.class);
    private static final EnumSet<ShapeType> SUPPORTED_TYPES = EnumSet.of(ShapeType.STRING);

    private static final Set<String> SUPPORTED_TRAITS = Set.of("com.hmellema.sgf4j.extensions#friendlyId");

    @Override
    public EnumSet<ShapeType> getSupportedShapeTypes() {
        return SUPPORTED_TYPES;
    }

    @Override
    public Set<String> getSupportedTraitNames() {
        return SUPPORTED_TRAITS;
    }

    @Override
    public void process(ShapeGenMetadata shapeGenMetadata, MetaDataLoader metaDataLoader) {
        shapeGenMetadata.setTypeName(UUID_TYPE_NAME);
    }
}

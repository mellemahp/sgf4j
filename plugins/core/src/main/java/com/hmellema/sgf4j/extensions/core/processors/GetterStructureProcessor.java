package com.hmellema.sgf4j.extensions.core.processors;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Processor;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.apache.commons.lang3.StringUtils;
import software.amazon.smithy.model.shapes.ShapeType;

import javax.lang.model.element.Modifier;
import java.util.EnumSet;
import java.util.Set;

public class GetterStructureProcessor implements Processor {
    private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.of(ShapeType.MEMBER);
    private static final String GETTER_TEMPLATE = "get%s";

    @Override
    public EnumSet<ShapeType> getSupportedShapeTypes() {
        return SUPPORTED_SHAPES;
    }

    @Override
    public Set<String> getSupportedTraitNames() {
        return Set.of("*");
    }

    @Override
    public void process(ShapeGenMetadata shapeGenMetadata, ShapeGenMetadataMap shapeGenMetadataMap) {
        shapeGenMetadata.addFieldMethod(methodSpecGenerator(shapeGenMetadata.asField(shapeGenMetadataMap)));
    }

    public MethodSpec methodSpecGenerator(FieldSpec fieldSpec) {
        return MethodSpec.methodBuilder(String.format(GETTER_TEMPLATE, StringUtils.capitalize(fieldSpec.name)))
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return this.$N", fieldSpec)
                .returns(fieldSpec.type)
                .build();
    }
}

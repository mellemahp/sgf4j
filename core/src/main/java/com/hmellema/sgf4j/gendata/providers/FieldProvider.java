package com.hmellema.sgf4j.gendata.providers;

import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import java.util.Collection;
import java.util.List;

public interface FieldProvider {
    // Returns a field spec to represent this shape as a field in another shape
    default FieldSpec asField(ShapeGenMetadataMap shapeGeneratorMap) {
        throw new UnsupportedOperationException("asField not defined for this metadata object");
    }

    // All methods associated with a shape's when it is used in a field definition
    List<MethodSpec> getFieldAssociatedMethods();

    void addFieldMethod(MethodSpec fieldMethod);

    // Adds annotations to the type when represented as a field
    List<AnnotationSpec> getFieldAnnotations();

    void addFieldAnnotation(AnnotationSpec annotationSpec);

    // TODO: Add field initializers
}

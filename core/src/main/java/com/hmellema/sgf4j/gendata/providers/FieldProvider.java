package com.hmellema.sgf4j.gendata.providers;

import com.hmellema.sgf4j.gendata.methodgenerators.FieldAssociatedMethodSpecGenerator;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import java.util.Collection;

public interface FieldProvider {
    // Returns a field spec to represent this shape as a field in another shape
    // A shape is included as field when it is a member of another shape
    default FieldSpec asField(String fieldName, ShapeGenMetadataMap shapeGeneratorMap) {
        throw new UnsupportedOperationException("asField not defined for this metadata object");
    }

    default Collection<MethodSpec> getClassAssociatedMethods(FieldSpec fieldSpec) {
        return getFieldAssociatedMethodGenerators().stream()
                .map(generator -> generator.generateMethod(fieldSpec))
                .toList();
    }

    // All methods associated with a shape's field definition to be used when it is added as a
    // field to another class. This could be getters/setters or some other utility method
    Collection<FieldAssociatedMethodSpecGenerator> getFieldAssociatedMethodGenerators();

    void addAssociatedMethodGenerator(FieldAssociatedMethodSpecGenerator specGenerator);

    // Adds annotations to the type when represented as a field
    Collection<AnnotationSpec> getFieldAnnotations();

    void addFieldAnnotation(AnnotationSpec annotationSpec);

    // TODO: Add field initializers
}

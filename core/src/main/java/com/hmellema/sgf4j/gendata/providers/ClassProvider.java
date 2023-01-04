package com.hmellema.sgf4j.gendata.providers;

import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClassProvider {
    // Some shapes can be represented as a standalone class
    // Shapes that cannot be represented as a standalone class should return Optional.empty()
    default Optional<TypeSpec> asClass(ShapeGenMetadataMap shapeGeneratorMap) {
        return Optional.empty();
    }

    // All methods associated with this shape when used as a standalone class
    // Not all shapes can have these methods
    default List<MethodSpec> getClassAssociatedMethods() {
        return Collections.emptyList();
    }

    default void addClassAssociatedMethod(MethodSpec methodSpec) {
        throw new UnsupportedOperationException("addClassAssociatedMethod not defined for this metadata object");
    }

    // Annotations associated with the shape when created as a standalone class
    default List<AnnotationSpec> getClassAnnotations() {
        return Collections.emptyList();
    }

    default void addClassAnnotation(AnnotationSpec annotationSpec) {
        throw new UnsupportedOperationException("addClassAnnotation not defined for this metadata object");
    }

    // Class to extend when represented as a class
    default Optional<TypeName> getParentClassType() {
        return Optional.empty();
    }

    default void setParentClassType(TypeName parentClassType) {
        throw new UnsupportedOperationException("setParentClassType not defined for this metadata object");
    }

    // Classes that are nested in the type when the type is generated as a standalone class
    // for example, a builder class.
    default List<TypeSpec> getNestedClasses() {
        return Collections.emptyList();
    }

    default void addNestedClass(TypeSpec nestedClass) {
        throw new UnsupportedOperationException("addNestedClass not defined for this metadata object");
    }

    // Get the types of each member field <Field name, TypeSpec>
    default Map<String, TypeName> getFieldTypes() {
        return Collections.emptyMap();
    }

    // allows modification of member field
    default void putFieldType(String fieldName, TypeName typeName)  {
        throw new UnsupportedOperationException("putFieldType not defined for this metadata object");
    }
}

package com.hmellema.sgf4j.gendata.providers;

import com.squareup.javapoet.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ClassProvider {
    // Some shapes can be represented as a standalone class
    // Shapes that cannot be represented as a standalone class should return Optional.empty()
    default Optional<TypeSpec> asClass() {
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

    default List<FieldSpec> getAdditionalClassFields() {
        return Collections.emptyList();
    }

    default void addAdditionalClassField(FieldSpec fieldSpec) {
        throw new UnsupportedOperationException("addAdditionalClassField not defined for this metadata object");
    }

}

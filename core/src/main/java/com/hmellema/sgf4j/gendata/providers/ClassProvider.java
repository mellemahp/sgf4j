package com.hmellema.sgf4j.gendata.providers;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClassProvider {
    // Some shapes can be represented as a standalone class
    // Shapes that cannot be represented as a standalone class should return Optional.empty()
    Optional<TypeSpec> asClass();

    // All methods associated with this shape when used as a standalone class
    List<MethodSpec> getClassAssociatedMethods();

    void addClassAssociatedMethod(MethodSpec methodSpec);

    // Annotations associated with the shape when created as a standalone class
    List<AnnotationSpec> getClassAnnotations();

    void addClassAnnotation(AnnotationSpec annotationSpec);

    // Class to extend when represented as a class
    Optional<TypeName> getParentClassType();

    void setParentClassType(TypeName parentClassType);

    // Classes that are nested in the type when the type is generated as a standalone class
    // for example, a builder class.
    List<TypeSpec> getNestedClasses();

    void addNestedClass(TypeSpec nestedClass);

    // Get the types of each member field <Field name, TypeSpec>
    Map<String, TypeName> getFieldTypes();

    // allows modification of member field
    void putFieldType(String fieldName, TypeName typeName);
}

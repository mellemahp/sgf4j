package com.hmellema.sgf4j.core.shapegenerator.providers;

import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.hmellema.sgf4j.core.shapegenerator.methodgenerators.ClassAssociatedMethodSpecGenerator;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.List;
import java.util.Optional;

public interface ClassProvider {
  // Some shapes can be represented as a standalone class
  // Shapes that cannot be represented as a standalone class should return Optional.empty()
  Optional<TypeSpec> asClass(ShapeGeneratorMap shapeGeneratorMap);

  // All methods associated with this shape when used as a standalone class
  default List<MethodSpec> getClassAssociatedMethods() {
    return getClassAssociatedMethodGenerator().stream()
        .map(ClassAssociatedMethodSpecGenerator::generateMethod)
        .toList();
  }

  // Generators for the class associated methods
  List<ClassAssociatedMethodSpecGenerator> getClassAssociatedMethodGenerator();
  void addClassAssociatedMethodGenerator(ClassAssociatedMethodSpecGenerator methodSpec);

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
}

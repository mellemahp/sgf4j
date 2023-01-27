package com.hmellema.sgf4j.extensions.core.shapegenmetadata;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.lang.model.element.Modifier;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.StructureShape;

public class StructureShapeGenMetadata extends ShapeGenMetadata {
  private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.of(ShapeType.STRUCTURE);


  private final List<AnnotationSpec> fieldAnnotations = new ArrayList<>();
  private final List<AnnotationSpec> classAnnotations = new ArrayList<>();
  private final List<MethodSpec> associatedMethods = new ArrayList<>();

  private final List<TypeSpec> nestedClasses = new ArrayList<>();

  private TypeName typeName;
  private String nameSpace;
  private final String className;

  private final List<FieldSpec> additionalFields = new ArrayList<>();

  private TypeName parentType;

  public StructureShapeGenMetadata(Shape shape, TypeName typeName) {
    super(shape, SUPPORTED_SHAPES);
    this.typeName = Objects.requireNonNull(typeName, "typeName cannot be null.");
    this.nameSpace = shape.getId().getNamespace();
    this.className = shape.getId().getName();
  }

  @Override
  public List<MethodSpec> getFieldAssociatedMethods() {
    return Collections.emptyList();
  }

  @Override
  public void addFieldMethod(MethodSpec fieldMethod) {
    throw new UnsupportedOperationException("unsupported");
  }

  @Override
  public List<AnnotationSpec> getFieldAnnotations() {
    return fieldAnnotations;
  }

  @Override
  public void addFieldAnnotation(AnnotationSpec annotationSpec) {
    Objects.requireNonNull(annotationSpec, "annotationSpec cannot be null.");
    fieldAnnotations.add(annotationSpec);
  }

  @Override
  public TypeName getTypeName() {
    return typeName;
  }

  @Override
  public void setTypeName(TypeName typeName) {
      this.typeName = Objects.requireNonNull(typeName, "typeName cannot be null.");
  }

  @Override
  public String getNameSpace() {
    return nameSpace;
  }

  @Override
  public void setNameSpace(String nameSpace) {
    this.nameSpace = Objects.requireNonNull(nameSpace, "nameSpace cannot be null.");
  }

  @Override
  public Optional<TypeSpec> asClass(ShapeGenMetadataMap shapeGenMetadataMap) {
    var specBuilder = TypeSpec.classBuilder(className)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

    for (var annotation : classAnnotations) {
      specBuilder.addAnnotation(annotation);
    }

    for (var associatedMethod : this.getClassAssociatedMethods()) {
      specBuilder.addMethod(associatedMethod);
    }

    var structureShape = (StructureShape) getShape();
    var memberShapes = structureShape.getAllMembers();
    for (var member : memberShapes.values()) {
      var memberData = shapeGenMetadataMap.get(member.getId())
          .orElseThrow(() -> new IllegalArgumentException("Tried to access unresolved shape " + member.getId()));
      var memberFieldType = memberData.asField();

      specBuilder.addField(memberFieldType);

      for (var associatedMethod : memberData.getFieldAssociatedMethods()) {
        specBuilder.addMethod(associatedMethod);
      }
    }

    // Add any additional class fields
    for (var classField : getAdditionalClassFields()) {
      specBuilder.addField(classField);
    }

    if (parentType != null) {
      specBuilder.superclass(parentType);
    }

    for (var nestedClass : nestedClasses) {
      specBuilder.addType(nestedClass);
    }

    return Optional.of(specBuilder.build());
  }

  @Override
  public List<MethodSpec> getClassAssociatedMethods() {
    return associatedMethods;
  }

  @Override
  public void addClassAssociatedMethod(MethodSpec methodSpec) {
    Objects.requireNonNull(methodSpec, "methodSpec cannot be null.");
    associatedMethods.add(methodSpec);
  }

  @Override
  public List<AnnotationSpec> getClassAnnotations() {
    return classAnnotations;
  }

  @Override
  public void addClassAnnotation(AnnotationSpec annotationSpec) {
    Objects.requireNonNull(annotationSpec, "annotationSpec cannot be null.");
    classAnnotations.add(annotationSpec);
  }

  @Override
  public Optional<TypeName> getParentClassType() {
    return Optional.ofNullable(parentType);
  }

  @Override
  public void setParentClassType(TypeName parentClassType) {
    parentType = Objects.requireNonNull(parentClassType);
  }

  @Override
  public List<TypeSpec> getNestedClasses() {
    return nestedClasses;
  }

  @Override
  public void addNestedClass(TypeSpec nestedClass) {
    Objects.requireNonNull(nestedClass, "nestedClass cannot be null.");
    nestedClasses.add(nestedClass);
  }

  @Override
  public List<FieldSpec> getAdditionalClassFields() {
    return additionalFields;
  }

  @Override
  public void addAdditionalClassField(FieldSpec fieldSpec) {
    Objects.requireNonNull(fieldSpec, "fieldSpec cannot be null.");
    additionalFields.add(fieldSpec);
  }
}

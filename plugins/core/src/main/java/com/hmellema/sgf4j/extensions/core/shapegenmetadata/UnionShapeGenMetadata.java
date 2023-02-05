package com.hmellema.sgf4j.extensions.core.shapegenmetadata;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.squareup.javapoet.*;

import java.util.*;

import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.UnionShape;

import javax.lang.model.element.Modifier;

public class UnionShapeGenMetadata extends ShapeGenMetadata {
  private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.of(ShapeType.UNION);

  private final List<FieldSpec> fields = new ArrayList<>();
  private final List<AnnotationSpec> fieldAnnotations = new ArrayList<>();
  private final List<AnnotationSpec> classAnnotations = new ArrayList<>();
  private final List<MethodSpec> associatedMethods = new ArrayList<>();

  private final List<TypeSpec> nestedClasses = new ArrayList<>();

  private TypeName typeName;
  private String nameSpace;
  private final String className;

  public UnionShapeGenMetadata(Shape shape, TypeName typeName, List<ShapeGenMetadata> memberDataList) {
    super(shape, SUPPORTED_SHAPES);
    this.typeName = Objects.requireNonNull(typeName, "typeName cannot be null.");
    this.nameSpace = shape.getId().getNamespace();
    this.className = shape.getId().getName();

    for (var memberData : memberDataList) {
      fields.add(memberData.asField());
      associatedMethods.addAll(memberData.getFieldAssociatedMethods());
    }


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
    Objects.requireNonNull(annotationSpec, "annotationSpec cannot be null");
    fieldAnnotations.add(annotationSpec);
  }

  @Override
  public TypeName getTypeName() {
    return typeName;
  }

  @Override
  public void setTypeName(TypeName typeName) {
    this.typeName = Objects.requireNonNull(typeName, "typeName cannot be null");
  }

  @Override
  public String getNameSpace() {
    return nameSpace;
  }

  @Override
  public void setNameSpace(String nameSpace) {
    this.nameSpace = Objects.requireNonNull(nameSpace, "nameSpace cannot be null");
  }

  @Override
  public Optional<TypeSpec> asClass() {
    var specBuilder = TypeSpec.classBuilder(className)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

    specBuilder.addAnnotations(classAnnotations);
    specBuilder.addMethods(associatedMethods);

    // Add fields
    specBuilder.addFields(fields);

    // Add any additional class fields
    specBuilder.addFields(getAdditionalClassFields());

    specBuilder.addTypes(nestedClasses);

    return Optional.of(specBuilder.build());
  }

  @Override
  public List<MethodSpec> getClassAssociatedMethods() {
    return associatedMethods;
  }

  @Override
  public void addClassAssociatedMethod(MethodSpec methodSpec) {
    Objects.requireNonNull(methodSpec, "methodSpec cannot be null");
    associatedMethods.add(methodSpec);
  }

  @Override
  public List<AnnotationSpec> getClassAnnotations() {
    return classAnnotations;
  }

  @Override
  public void addClassAnnotation(AnnotationSpec annotationSpec) {
    Objects.requireNonNull(annotationSpec, "annotationSpec cannot be null");
    classAnnotations.add(annotationSpec);
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
    return Collections.emptyList();
  }

  @Override
  public void addAdditionalClassField(FieldSpec fieldSpec) {
    throw new UnsupportedOperationException("unsupported");
  }
}

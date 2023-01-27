package com.hmellema.sgf4j.extensions.core.shapegenmetadata;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.EnumSet;

import java.util.List;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

public class MemberShapeGenMetadata extends ShapeGenMetadata {
  private static final EnumSet<ShapeType> SUPPORTED_SHAPE_TYPES = EnumSet.of(ShapeType.MEMBER);

  private final String memberName;
  private final ShapeId targetId;

  private TypeName typeName;

  private final List<AnnotationSpec> fieldAnnotations = new ArrayList<>();
  private final List<MethodSpec> fieldMethods = new ArrayList<>();

  public MemberShapeGenMetadata(Shape shape, ShapeGenMetadata targetMetadata) {
    super(shape, SUPPORTED_SHAPE_TYPES);
    var memberShape= (MemberShape) shape;

    this.memberName = memberShape.getMemberName();
    this.targetId = memberShape.getTarget();
    this.typeName = targetMetadata.getTypeName();

    // inherit all annotations from target as field annotations
    fieldAnnotations.addAll(targetMetadata.getFieldAnnotations());
  }

  public ShapeId getTargetId() {
    return this.targetId;
  }

  @Override
  public FieldSpec asField() {
    return FieldSpec.builder(typeName, memberName)
            .addModifiers(Modifier.PRIVATE)
            .addAnnotations(fieldAnnotations)
            .build(); // Modifier.FINAL  (with initializer?)
  }

  @Override
  public List<MethodSpec> getFieldAssociatedMethods() {
    return fieldMethods;
  }

  @Override
  public void addFieldMethod(MethodSpec fieldMethod) {
    Objects.requireNonNull(fieldMethod, "fieldMethod cannot be null");
    fieldMethods.add(fieldMethod);
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
    throw new UnsupportedOperationException("unsupported");
  }

  @Override
  public void setNameSpace(String nameSpace) {
    throw new UnsupportedOperationException("unsupported");
  }
}

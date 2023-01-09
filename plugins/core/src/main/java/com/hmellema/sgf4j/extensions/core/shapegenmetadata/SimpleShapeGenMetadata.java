package com.hmellema.sgf4j.extensions.core.shapegenmetadata;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class SimpleShapeGenMetadata extends ShapeGenMetadata {
  private static final EnumSet<ShapeType> SUPPORTED_SHAPE_TYPES = EnumSet.of(
      ShapeType.DOUBLE,
      ShapeType.BYTE,
      ShapeType.BIG_DECIMAL,
      ShapeType.BIG_INTEGER,
      ShapeType.BLOB,
      ShapeType.BOOLEAN,
      ShapeType.FLOAT,
      ShapeType.INTEGER,
      ShapeType.LONG,
      ShapeType.SHORT,
      ShapeType.TIMESTAMP,
      ShapeType.STRING,
      ShapeType.MAP,
      ShapeType.LIST
  );

  private final List<AnnotationSpec> fieldAnnotations = new ArrayList<>();

  private TypeName typeName;
  private String nameSpace;

  public SimpleShapeGenMetadata(Shape shape,
                                TypeName typeName
  ) {
    super(shape, SUPPORTED_SHAPE_TYPES);
    this.typeName = Objects.requireNonNull(typeName, "typeName cannot be null.");
    this.nameSpace = shape.getId().getNamespace();
  }


  @Override
  public List<MethodSpec> getFieldAssociatedMethods() {
    return Collections.emptyList();
  }

  @Override
  public void addFieldMethod(MethodSpec fieldMethod) {
    throw new UnsupportedOperationException("Unsupported");
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
}

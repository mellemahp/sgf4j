package com.hmellema.sgf4j.extensions.core.shapegenmetadata;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class OperationShapeGenMetadata extends ShapeGenMetadata {
  private static final EnumSet<ShapeType> SUPPORTED_TYPE = EnumSet.of(ShapeType.OPERATION);

  public OperationShapeGenMetadata(Shape shape) {
    super(shape, SUPPORTED_TYPE);
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
    return Collections.emptyList();
  }

  @Override
  public void addFieldAnnotation(AnnotationSpec annotationSpec) {
    throw new UnsupportedOperationException("unsupported");
  }

  @Override
  public TypeName getTypeName() {
    return null;
  }

  @Override
  public void setTypeName(TypeName typeName) {
    throw new UnsupportedOperationException("unsupported");
  }

  @Override
  public String getNameSpace() {
    return null;
  }

  @Override
  public void setNameSpace(String nameSpace) {
    throw new UnsupportedOperationException("unsupported");
  }
}

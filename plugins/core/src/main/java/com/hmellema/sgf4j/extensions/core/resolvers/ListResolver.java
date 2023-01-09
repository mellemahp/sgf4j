package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.extensions.core.util.TypeConversionUtil;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.Resolver;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import java.util.Set;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

// TODO: Handle sets?? (or maybe in processor?)
public class ListResolver implements Resolver {
  private static final ClassName BASE_LIST_CLASS_NAME = ClassName.get("java.util", "List");

  @Override
  public ShapeType getSupportedShapeType() {
    return ShapeType.LIST;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    var listShape = (ListShape) shape;
    return Set.of(listShape.getMember().getTarget());
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    var listType = extractListType((ListShape) shape, shapeGenMetadataMap);
    return new SimpleShapeGenMetadata(shape, listType);
  }

  private static TypeName extractListType(ListShape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    var memberType = TypeConversionUtil.boxPrimitive(
        shapeGenMetadataMap.get(shape.getMember().getTarget())
            .orElseThrow(
                () -> new IllegalArgumentException("Tried to access unresolved shape " + shape.getMember().getTarget()))
            .getTypeName()
    );
    return ParameterizedTypeName.get(BASE_LIST_CLASS_NAME, memberType);
  }
}

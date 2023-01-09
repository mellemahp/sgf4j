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
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

public class MapResolver implements Resolver {
  private static final ClassName BASE_MAP_CLASS_NAME = ClassName.get("java.util", "Map");

  @Override
  public ShapeType getSupportedShapeType() {
    return ShapeType.MAP;
  }

  @Override
  public Set<ShapeId> getDependentShapes(Shape shape) {
    var mapShape = (MapShape) shape;
    var keyId = mapShape.getKey().getTarget();
    var valId = mapShape.getValue().getTarget();
    return Set.of(keyId, valId);
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    var mapType = extractMapType((MapShape) shape, shapeGenMetadataMap);
    return new SimpleShapeGenMetadata(shape, mapType);
  }

  private static TypeName extractMapType(MapShape shape, ShapeGenMetadataMap shapeGenMetadataMap) {
    var keyType = TypeConversionUtil.boxPrimitive(
        shapeGenMetadataMap.get(shape.getKey().getTarget())
            .orElseThrow(
                () -> new IllegalArgumentException("Tried to access unresolved shape " + shape.getKey().getTarget()))
            .getTypeName()
    );
    var valueType = TypeConversionUtil.boxPrimitive(
        shapeGenMetadataMap.get(shape.getValue().getTarget())
            .orElseThrow(
                () -> new IllegalArgumentException("Tried to access unresolved shape " + shape.getValue().getTarget()))
            .getTypeName()
    );

    return ParameterizedTypeName.get(BASE_MAP_CLASS_NAME, keyType, valueType);
  }
}

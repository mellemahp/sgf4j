package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.extensions.core.util.TypeConversionUtil;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.resolving.Resolver;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class MapResolver implements Resolver {
  private static final ClassName BASE_MAP_CLASS_NAME = ClassName.get("java.util", "Map");

  @Override
  public ShapeType getSupportedShapeType() {
    return ShapeType.MAP;
  }

  @Override
  public ShapeGenMetadata resolve(Shape shape, MetaDataLoader metaDataLoader) {
    var mapType = extractMapType((MapShape) shape, metaDataLoader);
    return new SimpleShapeGenMetadata(shape, mapType);
  }

  private static TypeName extractMapType(MapShape shape, MetaDataLoader metaDataLoader) {
    var keyType = TypeConversionUtil.boxPrimitive(metaDataLoader.resolve(shape.getKey().getTarget()).getTypeName());
    var valueType = TypeConversionUtil.boxPrimitive(metaDataLoader.resolve(shape.getValue().getTarget()).getTypeName());
    return ParameterizedTypeName.get(BASE_MAP_CLASS_NAME, keyType, valueType);
  }
}

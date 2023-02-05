package com.hmellema.sgf4j.extensions.core.resolvers;

import com.hmellema.sgf4j.extensions.core.shapegenmetadata.SimpleShapeGenMetadata;
import com.hmellema.sgf4j.extensions.core.util.TypeConversionUtil;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.resolving.Resolver;
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
  public ShapeGenMetadata resolve(Shape shape, MetaDataLoader metaDataLoader) {
    var listType = extractListType((ListShape) shape, metaDataLoader);
    return new SimpleShapeGenMetadata(shape, listType);
  }

  private static TypeName extractListType(ListShape shape, MetaDataLoader metaDataLoader) {
    var memberType = TypeConversionUtil.boxPrimitive(metaDataLoader.resolve(shape.getMember().getTarget()).getTypeName());
    return ParameterizedTypeName.get(BASE_LIST_CLASS_NAME, memberType);
  }
}

package com.hmellema.sgf4j.extensions.core.shapegenmetadata;

import com.hmellema.sgf4j.extensions.core.util.TypeConversionUtil;
import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.lang.model.element.Modifier;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.traits.EnumValueTrait;

public class EnumShapeGenMetadata extends ShapeGenMetadata {
  private static final String VALUE_FIELD_NAME = "value";
  private static final EnumSet<ShapeType> SUPPORTED_SHAPES = EnumSet.of(ShapeType.ENUM, ShapeType.INT_ENUM);
  private final List<AnnotationSpec> fieldAnnotations = new ArrayList<>();
  private final List<AnnotationSpec> classAnnotations = new ArrayList<>();
  private final List<MethodSpec> associatedMethods = new ArrayList<>();

  private TypeName typeName;
  private String nameSpace;
  private String className;

  public EnumShapeGenMetadata(Shape shape) {
    super(shape, SUPPORTED_SHAPES);
    this.typeName = TypeConversionUtil.extractStandaloneTypeName(shape);
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
    Objects.requireNonNull(fieldAnnotations);
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
    this.nameSpace = nameSpace;
  }

  @Override
  public Optional<TypeSpec> asClass(ShapeGenMetadataMap metadataMap) {
    var specBuilder = TypeSpec.enumBuilder(className)
        .addModifiers(Modifier.PUBLIC);

    for (var annotation : classAnnotations) {
      specBuilder.addAnnotation(annotation);
    }

    for (var associatedMethod: this.getClassAssociatedMethods()) {
      specBuilder.addMethod(associatedMethod);
    }

    // CREATE CONSTRUCTOR
    switch (getShape().getType()) {
      case ENUM -> {
        specBuilder.addField(String.class, VALUE_FIELD_NAME, Modifier.PRIVATE, Modifier.FINAL);
        specBuilder.addMethod(MethodSpec.constructorBuilder()
            .addParameter(ParameterSpec.builder(String.class, VALUE_FIELD_NAME, Modifier.FINAL).build())
            .addStatement("this.$N = $N", VALUE_FIELD_NAME, VALUE_FIELD_NAME)
            .build());
      }
      case INT_ENUM -> {
        specBuilder.addField(int.class, VALUE_FIELD_NAME, Modifier.PRIVATE, Modifier.FINAL);
        specBuilder.addMethod(MethodSpec.constructorBuilder()
            .addParameter(ParameterSpec.builder(int.class,VALUE_FIELD_NAME, Modifier.FINAL).build())
            .addStatement("this.$N = $N", VALUE_FIELD_NAME, VALUE_FIELD_NAME)
            .build());
      }
      default -> throw new IllegalStateException("Unsupported enum value type. Not string or int");
    }



    for (var memberEntry : getShape().getAllMembers().entrySet()) {
      // TODO: this is very clunky. Improve.
      var enumConstName = memberEntry.getKey();
      var memberShape = memberEntry.getValue();

      EnumValueTrait trait = memberShape.getTrait(EnumValueTrait.class).orElseThrow(
          () -> new IllegalStateException("No enum value defined for enum member entry")
      );

      TypeSpec enumValueSpec;
      switch (getShape().getType()) {
        case ENUM -> {
          var traitValue = trait.getStringValue().orElseThrow(() -> {
            throw new IllegalStateException("Expected int value for enum");
          });
          enumValueSpec = TypeSpec.anonymousClassBuilder("$S", traitValue).build();
        }
        case INT_ENUM -> {
          int traitValue = trait.getIntValue().orElseThrow(() -> {
            throw new IllegalStateException("Expected int value for enum");
          });
          enumValueSpec = TypeSpec.anonymousClassBuilder("$L", traitValue).build();
        }
        default -> throw new IllegalStateException("Unsupported enum value type. Not string or int");
      }
      // END TODO

      specBuilder.addEnumConstant(enumConstName, enumValueSpec);
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
}

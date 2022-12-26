package com.hmellema.sgf4j.core.generator.executors;

import com.hmellema.sgf4j.core.generator.exceptions.GenerationException;
import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.squareup.javapoet.JavaFile;
import java.io.IOException;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.processing.Filer;

public class GenerationExecutor {
  private final Filer filer;

  public GenerationExecutor(Filer filer) {
    this.filer = Objects.requireNonNull(filer, "filer cannot be null");
  }

  public void generate(ShapeGeneratorMap shapeGeneratorMap) {
    shapeGeneratorMap.generators().stream()
            .map(fileGenerator -> fileGenerator.generate(shapeGeneratorMap))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(this::writeFile);
  }

  private void writeFile(JavaFile generatedFile) {
    try {
      generatedFile.writeTo(filer);
    } catch (IOException e) {
      throw new GenerationException("Code generation failed for Java file" + generatedFile, e);
    }
  }

//  private record TypespecAssemblyResult(AbstractShapeGenerator shapeGen, TypeSpec.Builder typeDefBuilder) {}

//  private List<JavaFile> generateFiles(ShapeGeneratorMap shapeGenMap) {
//    List<JavaFile> fileList = new ArrayList<>();
//    for (var shapeGen : shapeGenMap.values()) {
//      shapeGen.asType(shapeGenMap)
//          .map(TypeSpec::toBuilder)
//          .map(typeSpecBuilder -> new TypespecAssemblyResult(shapeGen, typeSpecBuilder))
//          .map(this::addClassMethods)
//          .map(this::addNestedClasses)
//          .map(this::generateJavaFile)
//          .ifPresent(fileList::add);
//    }
//    return fileList;
//  }
//
//  private TypespecAssemblyResult addClassMethods(TypespecAssemblyResult result) {
//    result.shapeGen.getClassMethods().forEach(result.typeDefBuilder::addMethod);
//    return result;
//  }
//
//  private TypespecAssemblyResult addNestedClasses(TypespecAssemblyResult result) {
//    result.shapeGen.getNestedClasses().forEach(result.typeDefBuilder::addType);
//    return result;
//  }

//  private JavaFile generateJavaFile(TypespecAssemblyResult result) {
//    return JavaFile.builder(
//        result.shapeGen.getShape().getId().getNamespace(),
//        result.typeDefBuilder.build()
//    ).build();
//  }


}

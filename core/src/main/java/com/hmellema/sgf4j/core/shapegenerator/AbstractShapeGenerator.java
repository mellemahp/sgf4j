package com.hmellema.sgf4j.core.shapegenerator;

import com.hmellema.sgf4j.core.generator.JavaFileGenerator;
import com.hmellema.sgf4j.core.mapping.ShapeGeneratorMap;
import com.hmellema.sgf4j.core.shapegenerator.providers.ClassProvider;
import com.hmellema.sgf4j.core.shapegenerator.providers.FieldProvider;
import com.hmellema.sgf4j.core.shapegenerator.providers.ParameterProvider;
import com.hmellema.sgf4j.core.shapegenerator.providers.ShapeProvider;
import com.squareup.javapoet.JavaFile;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractShapeGenerator
        implements ShapeProvider, ClassProvider, FieldProvider, ParameterProvider, JavaFileGenerator {

    private final Map<Class<?>, String> staticImports = new HashMap<>();

    private final EnumSet<ShapeType> supportedShapes;
    private final Shape shape;
    private final String nameSpace;
    @Nullable
    private String fileComment;

    protected AbstractShapeGenerator(Shape shape, EnumSet<ShapeType> supportedShapes) {
        this.shape = Objects.requireNonNull(shape, "shape cannot be null.");
        this.nameSpace = shape.getId().getNamespace();
        Objects.requireNonNull(supportedShapes, "supportedShapes cannot be null.");
        if (supportedShapes.isEmpty()) {
            throw new IllegalArgumentException("supportedShapes cannot be empty");
        }
        this.supportedShapes = supportedShapes;

        if (!supportedShapes.contains(shape.getType())) {
            throw new IllegalStateException("Cannot create shape generator for unsupported type: " + shape.getType());
        }
    }

    public EnumSet<ShapeType> getSupportedShapes() {
        return this.supportedShapes;
    }

    @Override
    public Shape getShape() {
        return this.shape;
    }

    @Override
    public ShapeId getShapeId() {
        return this.shape.getId();
    }

    @Override
    public ShapeType getShapeType() {
        return this.shape.getType();
    }

    public String putStaticImport(Class<?> clazz, String str) {
        Objects.requireNonNull(clazz, "clazz cannot be null.");
        Objects.requireNonNull(str, "str cannot be null.");
        return this.staticImports.put(clazz, str);
    }

    public void setFileComment(String commment) {
        this.fileComment = Objects.requireNonNull(commment, "comment cannot be null.");
    }

    @Override
    public Optional<JavaFile> generate(ShapeGeneratorMap generatorMap) {
        var typeOptional = asClass(generatorMap);

        if (typeOptional.isPresent()) {
            final JavaFile.Builder fileBuilder = JavaFile.builder(nameSpace, typeOptional.get());

            // Add any comments for the overall class
            if (fileComment != null) {
                fileBuilder.addFileComment(fileComment);
            }

            // Add any static imports to the top of the file
            if (!staticImports.isEmpty()) {
                for (var staticImportEntry : staticImports.entrySet()) {
                    fileBuilder.addStaticImport(staticImportEntry.getKey(), staticImportEntry.getValue());
                }
            }

            return Optional.of(fileBuilder.build());
        }

        return Optional.empty();
    }

    public abstract Optional<ShapeId> getTargetId();
}

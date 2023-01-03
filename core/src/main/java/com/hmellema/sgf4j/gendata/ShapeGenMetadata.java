package com.hmellema.sgf4j.gendata;

import com.hmellema.sgf4j.gendata.providers.*;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;

import java.util.EnumSet;
import java.util.Objects;

public abstract class ShapeGenMetadata implements
        JavaFileProvider, ClassProvider, FieldProvider, ParameterProvider, TypeProvider
{
    private final Shape shape;

    protected ShapeGenMetadata(Shape shape, EnumSet<ShapeType> allowedShapeTypes) {
        Objects.requireNonNull(shape, "shape cannot be null.");
        Objects.requireNonNull(allowedShapeTypes, "allowedShapeTypes cannot be null.");
        if (!allowedShapeTypes.contains(shape.getType())) {
            throw new IllegalArgumentException("Shape type: " + shape.getType() + "is not permitted. Must be one of: " + allowedShapeTypes);
        }
        this.shape = shape;
    }

    public Shape getShape() {
        return this.shape;
    }

    public ShapeId getShapeId() {
        return this.shape.getId();
    }

    public ShapeType getShapeType() {
        return this.shape.getType();
    }
}

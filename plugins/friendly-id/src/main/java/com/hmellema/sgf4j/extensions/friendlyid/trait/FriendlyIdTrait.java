package com.hmellema.sgf4j.extensions.friendlyid.trait;

import software.amazon.smithy.model.FromSourceLocation;
import software.amazon.smithy.model.SourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.StringTrait;

public class FriendlyIdTrait extends StringTrait {
    public static final ShapeId ID = ShapeId.from("com.hmellema.sgf4j.extensions#friendlyId");

    public FriendlyIdTrait(String value, FromSourceLocation sourceLocation) {
        super(ID, value, sourceLocation);
    }

    public FriendlyIdTrait(String value) {
        this(value, SourceLocation.NONE);
    }
}

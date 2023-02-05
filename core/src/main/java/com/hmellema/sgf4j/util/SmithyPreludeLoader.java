package com.hmellema.sgf4j.util;

import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.Prelude;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.BoxTrait;

import java.util.Objects;

public interface SmithyPreludeLoader {
    String PRELUDE_NAME = "prelude.smithy";
    static Model getPrelude() {
        return Model.assembler()
                .disablePrelude()
                .disableValidation()
                .addImport(Objects.requireNonNull(Prelude.class.getResource(PRELUDE_NAME)))
                // Patch in synthetic box traits for v1 compatibility.
                .addTrait(ShapeId.from("smithy.api#Boolean"), new BoxTrait())
                .addTrait(ShapeId.from("smithy.api#Byte"), new BoxTrait())
                .addTrait(ShapeId.from("smithy.api#Short"), new BoxTrait())
                .addTrait(ShapeId.from("smithy.api#Integer"), new BoxTrait())
                .addTrait(ShapeId.from("smithy.api#Long"), new BoxTrait())
                .addTrait(ShapeId.from("smithy.api#Float"), new BoxTrait())
                .addTrait(ShapeId.from("smithy.api#Double"), new BoxTrait())
                .assemble()
                .unwrap();
    }
}

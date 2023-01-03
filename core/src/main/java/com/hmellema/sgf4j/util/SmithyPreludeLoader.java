package com.hmellema.sgf4j.util;

import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.Prelude;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.BoxTrait;

public interface SmithyPreludeLoader {
    static Model getPrelude() {
        return Model.assembler()
                .disablePrelude()
                // Model validation is disabled when loading the prelude
                // because the prelude is validated during unit tests and
                // the prelude is immutable. However, if the prelude is
                // broken for whatever reason, ERROR events encountered
                // when performing model validation that uses the prelude
                // will still cause an error, meaning the prelude is still
                // validated when actually loading and using other models.
                .disableValidation()
                .addImport(Prelude.class.getResource("prelude.smithy"))
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

package com.hmellema.sgf4j.core.shapegenerator.providers;

import com.squareup.javapoet.TypeName;

public interface TypeProvider {
    // Gets a type to describe the shape as
    TypeName getTypeName();

    // Allows the overwriting of type name
    void setTypeName(TypeName typeName);

    // Gets a package namespace for the type
    String getNameSpace();

    // Allows for overwriting the type namespace
    void setNameSpace(String nameSpace);
}

package com.hmellema.sgf4j.api;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.mapping.ShapeGenMetadataMap;
import com.hmellema.sgf4j.util.ExtensionLoader;
import com.squareup.javapoet.JavaFile;

import java.util.List;
import java.util.Optional;

public interface Sgf4jGenerator {
    /** Generates java files from a model
     *
     * @param request generation request
     * @return List of generated files
     */
    static List<JavaFile> generate(Sgf4jGenerationRequest request) {
        var shapeGenMap = new ShapeGenMetadataMap(request.model());
        var extensions = ExtensionLoader.load(request.classLoader());
        for (var extension : extensions) {
            if (nonNullOrEmpty(extension.getResolvers())) {
                shapeGenMap.registerAllResolvers(extension.getResolvers());
            }
            if (nonNullOrEmpty(extension.getProcessors())) {
                shapeGenMap.registerAllProcessors(extension.getProcessors());
            }
        }

        shapeGenMap.resolve();

        return generate(shapeGenMap);
    }

    static boolean nonNullOrEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }


    /** Generates java files from a resolved shapeGenMetadataMap
     *
     * @param shapeGenMetadataMap resolved and processed map of metadata needed to generate shape
     * @return List of java files generated by shape metadata map
     */
    static List<JavaFile> generate(ShapeGenMetadataMap shapeGenMetadataMap) {
        if (!shapeGenMetadataMap.isResolved()) {
            throw new IllegalArgumentException("JavaFileGenerator can only run on a resolved shape map. Run shapeMap.resolve() to resolve.");
        }

        return shapeGenMetadataMap.values().stream()
                .map(Sgf4jGenerator::generate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    /** Generate the JavaPoet {@link JavaFile} for a shape
     *
     * @param shapeGenMetadata input shape metadata
     * @return Optional of the generated {@link JavaFile}. If the shape does not represent a standalone java class this should return Optional.empty
     */
    static Optional<JavaFile> generate(ShapeGenMetadata shapeGenMetadata) {
        var typeOptional = shapeGenMetadata.asClass();

        if (typeOptional.isPresent()) {
            final JavaFile.Builder fileBuilder = JavaFile.builder(shapeGenMetadata.getNameSpace(), typeOptional.get());

            // Add any comments for the overall class
            shapeGenMetadata.getFileComment().ifPresent(fileBuilder::addFileComment);

            // Add any static imports to the top of the file
            if (!shapeGenMetadata.getStaticImports().isEmpty()) {
                for (var staticImportEntry : shapeGenMetadata.getStaticImports().entrySet()) {
                    fileBuilder.addStaticImport(staticImportEntry.getKey(), staticImportEntry.getValue());
                }
            }

            return Optional.of(fileBuilder.build());
        }

        return Optional.empty();
    }
}
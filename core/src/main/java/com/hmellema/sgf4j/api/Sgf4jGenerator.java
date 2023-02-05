package com.hmellema.sgf4j.api;

import com.hmellema.sgf4j.gendata.ShapeGenMetadata;
import com.hmellema.sgf4j.loader.MetaDataLoader;
import com.hmellema.sgf4j.util.ExtensionLoader;
import com.squareup.javapoet.JavaFile;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.neighbor.Walker;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.ShapeId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Sgf4jGenerator {

    /** Generates java files from a model
     *
     * @param request generation request
     * @return List of generated files
     */
    static List<JavaFile> generate(Sgf4jGenerationRequest request) {
        // Initialize metadata loader and processors
        var metadataLoader = new MetaDataLoader(request.model());
        ExtensionLoader.loadResolverExtensions().forEach(metadataLoader::register);
        ExtensionLoader.loadProcessorExtensions().forEach(metadataLoader::register);

        // Resolve model to metadata by starting at the Service shape and walking the tree to find
        // closure of shapes required by service shape
        var walker = new Walker(request.model());
        var serviceShape = getServiceShape(request.model());
        Set<ShapeId> serviceClosure = walker.walkShapeIds(serviceShape);
        Set<ShapeGenMetadata> resolvedClosure = metadataLoader.resolve(serviceClosure);

        // Generate java file set from processed closure
        return generate(resolvedClosure);
    }

    /** Extracts a single service shape from a smithy model
     *
     * NOTE: multiple services are not supported for generation at this time
     *
     * @param model smithy {@link Model} to extract the service shape from
     * @return {@link ServiceShape} object extracted from model
     */
    static ServiceShape getServiceShape(Model model) {
        return model.getServiceShapes().stream()
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple services detected in smithy AST: " + a + ", " + b);
                })
                .orElseThrow(() -> new IllegalStateException("No service found for smithy ast. At least one service is required"));
    }

    static List<JavaFile> generate(Set<ShapeGenMetadata> shapeGenMetadataClosure) {
        return shapeGenMetadataClosure.stream()
                .map(Sgf4jGenerator::generate)
                .flatMap(Optional::stream)
                .toList();
    }

    /** Generate the JavaPoet {@link JavaFile} for a shape
     *
     * @param shapeGenMetadata input shape metadata
     * @return Optional of the generated {@link JavaFile}. If the shape does not represent a standalone java class this should return {@code Optional.EMPTY}
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

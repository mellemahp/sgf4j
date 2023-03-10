package com.hmellema.sgf4j.processor;

import com.google.auto.service.AutoService;
import com.hmellema.sgf4j.annotations.Sgf4j;
import com.hmellema.sgf4j.api.Sgf4jGenerationRequest;
import com.hmellema.sgf4j.api.Sgf4jGenerator;
import com.hmellema.sgf4j.util.ModelLoader;
import com.hmellema.sgf4j.processor.exceptions.FailToLoadAstException;
import com.squareup.javapoet.JavaFile;
import software.amazon.smithy.model.transform.ModelTransformer;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes(Sgf4j.NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class Sgf4jAnnotationProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        messager.printMessage(Diagnostic.Kind.NOTE,
                "initialized processor: " + this.getClass().getSimpleName() + "...");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var elements = roundEnv.getElementsAnnotatedWith(Sgf4j.class);
        if (elements.size() != 1) {
            if (elements.size() > 1) {
                messager.printMessage(Diagnostic.Kind.ERROR,
                        "Only one package can have generator annotation.");
            }
            return false;
        }

        final Sgf4jGenerationRequest request = extractRequestFromAnnotation(getAnnotation(elements));
        messager.printMessage(Diagnostic.Kind.NOTE, "Smithy AST loaded. Generating Java files...");
        var javaFiles = Sgf4jGenerator.generate(request);
        messager.printMessage(Diagnostic.Kind.NOTE, "Java files generated. Writing files...");
        javaFiles.forEach(this::writeFile);
        messager.printMessage(Diagnostic.Kind.NOTE,
                "Annotation processor " + this.getClass().getSimpleName() + " finished processing.");
        return true;
    }

    private Sgf4j getAnnotation(Set<? extends Element> elements) {
        return elements.stream()
                .findFirst()
                .map(element -> element.getAnnotation(Sgf4j.class))
                .orElseThrow(() -> new IllegalStateException("No annotation found on element"));
    }

    private Sgf4jGenerationRequest extractRequestFromAnnotation(final Sgf4j annotation) {
        var unfilteredModel = ModelLoader.load(getSmithyFileResourceURL(annotation.astPath()));
        var filteredModel = ModelTransformer.create().getModelWithoutTraitShapes(unfilteredModel);
        return new Sgf4jGenerationRequest(filteredModel, Arrays.stream(annotation.filters()).toList());
    }

    private void writeFile(JavaFile generatedFile) {
        try {
            generatedFile.writeTo(filer);
        } catch (IOException e) {
            throw new RuntimeException("Code generation failed for Java file" + generatedFile, e);
        }
    }

    private URL getSmithyFileResourceURL(final String path) {
        try {
            return filer.getResource(StandardLocation.ANNOTATION_PROCESSOR_PATH, "", path)
                    .toUri()
                    .toURL();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    "Unable to load file: " + path + " ERROR: " + e.getLocalizedMessage());
            throw new FailToLoadAstException("Smithy AST file failed to load.", e);
        }
    }
}

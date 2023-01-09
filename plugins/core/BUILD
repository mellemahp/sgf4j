load("//plugins:constants.bzl", "JAVAC_OPTS")
load("@rules_jvm_external//:defs.bzl", "artifact")

java_library(
    name = "anvil-plugin-core",
    srcs = glob(["src/main/java/com/hmellema/**/*.java"]),
    javacopts = JAVAC_OPTS,
    plugins = ["//plugins:google_auto_service_plugin"],
    visibility = ["//visibility:public"],
    deps = [
        "//plugins:nullaway",
        # pulls in nullable annotation
        artifact("com.google.code.findbugs:jsr305"),
        "@sgf4j//core:sgf4j-core",
        artifact("com.squareup:javapoet"),
        artifact("software.amazon.smithy:smithy-model"),
        artifact("org.apache.commons:commons-lang3"),
        # TODO: move to its own module
        artifact("com.fasterxml.jackson.core:jackson-annotations"),
        artifact("com.google.auto.service:auto-service-annotations")
    ],
)

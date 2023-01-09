load("//:constants.bzl", "JAVAC_OPTS")
load("@rules_jvm_external//:defs.bzl", "artifact")

java_library(
    name = "sgf4j-core",
    srcs = glob(["src/main/**/*.java"]),
    deps = [
        # nullness checking
        "//:nullaway",
        # pulls in nullable annotation
        artifact("com.google.code.findbugs:jsr305"),
        artifact("com.squareup:javapoet"),
        artifact("software.amazon.smithy:smithy-model"),
        artifact("org.pf4j:pf4j")
    ],
    javacopts = JAVAC_OPTS,
    visibility = ["//visibility:public"]
)
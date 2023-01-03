load("//:constants.bzl", "JAVAC_OPTS")
load("@rules_jvm_external//:defs.bzl", "artifact")
load("@rules_pmd//pmd:defs.bzl", "pmd")

java_library(
    name = "sgf4j-core",
    srcs = glob(["src/main/**/*.java"]),
    deps = [
        # nullness checking
        "//:nullaway",
        # pulls in nullable annotation
        artifact("com.google.code.findbugs:jsr305"),
        artifact("com.squareup:javapoet"),
        artifact("software.amazon.smithy:smithy-model")
    ],
    javacopts = JAVAC_OPTS,
    visibility = ["//visibility:public"],
)

pmd(
    name = "pmd_analysis",
    srcs =  glob(["src/main/**/*.java"]),
    rulesets = ["//:pmd-rulesets"],
)
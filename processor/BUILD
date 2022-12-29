load("//:constants.bzl", "JAVAC_OPTS")
load("@rules_jvm_external//:defs.bzl", "artifact")

java_plugin(
    name = "google_auto_service_plugin",
    processor_class = "com.google.auto.service.processor.AutoServiceProcessor",
    deps = [
        artifact("com.google.auto.service:auto-service")
    ]
)

java_library(
    name = "sgf4j-annotation-processing",
    srcs = glob(["src/main/java/com/hmellema/sgf4j/**/*.java"]),
    plugins = [":google_auto_service_plugin"],
    deps = [
        # nullness checking
        "//:nullaway",
        # base deps
        "//annotations:sgf4j-annotations",
        "//core:sgf4j-core",
        artifact("com.google.auto.service:auto-service-annotations")
    ],
    javacopts = JAVAC_OPTS,
    visibility = ["//visibility:public"],
)
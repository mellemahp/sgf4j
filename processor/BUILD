load("//:constants.bzl", "JAVAC_OPTS")

java_plugin(
    name = "google_auto_service_plugin",
    processor_class = "com.google.auto.service.processor.AutoServiceProcessor",
    deps = ["@maven//:com_google_auto_service_auto_service"]
)

java_library(
    name = "sgf4j-annotation-processing",
    srcs = glob(["src/main/java/com/hmellema/sgf4j/**/*.java"]),
    plugins = [
        ":nullaway_plugin",
        ":google_auto_service_plugin"
    ],
    deps = [
        "//annotations:sgf4j-annotations",
        "//core:sgf4j-core",
        "@maven//:com_google_auto_service_auto_service_annotations"
    ],
    javacopts = JAVAC_OPTS,
    visibility = ["//visibility:public"],
)
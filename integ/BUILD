load("@rules_jvm_external//:defs.bzl", "artifact")

java_plugin(
        name = "sgf4j_plugin",
        generates_api = 1,
        processor_class = "com.hmellema.sgf4j.processor.Sgf4jAnnotationProcessor",
        resources = [
            "//integ/model:ast_spec"
        ],
        deps = [
            "@sgf4j//processor:sgf4j-annotation-processing",
            "//plugins/core:sgf4j-plugin-core_deploy.jar",
            artifact("org.slf4j:slf4j-api"),
            artifact("ch.qos.logback:logback-classic"),
        ],
)

java_library(
    name = "test_lib",
    srcs = glob(["src/**/*.java"]),
    plugins = [":sgf4j_plugin"],
    deps = ["//annotations:sgf4j-annotations"],
)
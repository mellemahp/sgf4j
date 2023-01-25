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
            "//plugins/bean-validation:bean-validation-plugin_deploy.jar",
            "//plugins/jackson:jackson-plugin_deploy.jar",
            "//plugins/friendly-id:friendly-id-plugin_deploy.jar",
            artifact("org.slf4j:slf4j-api"),
            artifact("ch.qos.logback:logback-classic")
        ],
)

java_library(
    name = "test_lib",
    srcs = glob(["src/**/*.java"]),
    plugins = [":sgf4j_plugin"],
    deps = [
        "//annotations:sgf4j-annotations",
        # added to support bean validation plugin
        artifact("jakarta.validation:jakarta.validation-api"),
        # added to support jackson plugin
        artifact("com.fasterxml.jackson.core:jackson-annotations")
    ],
)
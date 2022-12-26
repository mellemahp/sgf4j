load("//:constants.bzl", "JAVAC_OPTS")

java_library(
    name = "sgf4j-core",
    srcs = glob(["src/main/**/*.java"]),
    plugins = [":nullaway_plugin"],
    deps = [
        # pulls in nullable annotation
        "@maven//:com_google_code_findbugs_jsr305",
        "@maven//:com_squareup_javapoet",
        "@maven//:software_amazon_smithy_smithy_model",
        "@maven//:org_pf4j_pf4j",
    ],
    javacopts = JAVAC_OPTS,
    visibility = ["//visibility:public"],
)
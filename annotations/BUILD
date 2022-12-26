load("//:constants.bzl", "JAVAC_OPTS")

java_library(
    name = "sgf4j-annotations",
    srcs = glob(["src/main/**/*.java"]),
    plugins = [":nullaway_plugin"],
    javacopts = JAVAC_OPTS,
    visibility = ["//visibility:public"]
)
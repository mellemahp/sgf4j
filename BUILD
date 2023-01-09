load("@rules_jvm_external//:defs.bzl", "artifact")

java_plugin(
    name = "nullaway_plugin",
    deps = [
        artifact("com.uber.nullaway:nullaway")
    ],
    visibility = ["//:__subpackages__"]
)

java_library(
    name = "nullaway",
    exported_plugins = ["nullaway_plugin"],
    neverlink = True,
    visibility = ["//visibility:public"],
)
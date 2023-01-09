load("@rules_jvm_external//:defs.bzl", "artifact")

java_plugin(
    name = "nullaway_plugin",
    visibility = ["//:__subpackages__"],
    deps = [ artifact("com.uber.nullaway:nullaway"), ],
)

java_library(
    name = "nullaway",
    exported_plugins = ["nullaway_plugin"],
    neverlink = True,
    visibility = ["//:__subpackages__"],
)

java_plugin(
    name = "pf4j_plugin",
    generates_api = 1,
    visibility = ["//:__subpackages__"],
    processor_class = "org.pf4j.processor.ExtensionAnnotationProcessor",
    deps = [ artifact("org.pf4j:pf4j") ],
)

load("//plugins:constants.bzl", "JAVAC_OPTS")
load("@rules_jvm_external//:defs.bzl", "artifact")

java_binary(
    name = "friendly-id-plugin",
    srcs = glob(["src/main/java/com/hmellema/**/*.java"]),
    javacopts = JAVAC_OPTS,
    plugins = ["//plugins:pf4j_plugin"],
    visibility = ["//visibility:public"],
    deps = [
        "//plugins:nullaway",
        # pulls in nullable annotation
        artifact("com.google.code.findbugs:jsr305"),
        "//core:sgf4j-core",
        # core codegen deps
        artifact("com.squareup:javapoet"),
        artifact("software.amazon.smithy:smithy-model"),
        artifact("org.pf4j:pf4j"),
    ],
    deploy_manifest_lines = [
          "Plugin-Class: com.hmellema.sgf4j.extensions.friendlyid.FriendlyIdPlugin",
          "Plugin-Id: friendly-id-plugin",
          "Plugin-Provider: Hunter Mellema",
          "Plugin-Version: 0.0.1"
    ],
    main_class = "does.not.exist",
)

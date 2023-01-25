package(default_visibility = ["//visibility:public"])

load("@smithy_rules//smithy:smithy.bzl", "smithy_ast")

SERVICE_BUILD_FILE = "smithy-build.json"

filegroup(
    name = "model_files",
    srcs = ["service.smithy"] + glob([
        "common/*.smithy",
    ]) + glob([
        "errors/*.smithy",
    ]) + glob([
        "resources/*.smithy",
    ]) + glob([
        "operations/*.smithy",
    ]),
)

smithy_ast(
    name = "ast_spec",
    srcs = [":model_files"],
    deps = ["//plugins/friendly-id/model:friendly_id_smithy_lib"],
    config = SERVICE_BUILD_FILE,
    projection = "model"
)

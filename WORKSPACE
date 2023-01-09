workspace(name = "sgf4j")

#######################
# Base bazel tool imports
#######################
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

#######################
# Setup Smithy Rules
#######################
SMITHY_RULES_TAG = "2.2.0"

SMITHY_RULES_SHA = "ff27ddba56792be7d80697151176089db1d5685a16ed7514dcc66c67fc51b616"

http_archive(
    name = "smithy_rules",
    sha256 = SMITHY_RULES_SHA,
    url = "https://github.com/mellemahp/smithy-bazel-rules/releases/download/%s/release.tar" % SMITHY_RULES_TAG,
)

load("@smithy_rules//smithy:deps.bzl", "smithy_cli_init")

smithy_cli_init()

#######################
# Java Library Imports
#######################
# import libraries from maven
RULES_JVM_EXTERNAL_TAG = "4.2"

RULES_JVM_EXTERNAL_SHA = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

#####################
# Java Dependencies #
#####################
load("//:dependencies.bzl", "JAVA_DEPENDENCIES", "MAVEN_REPOS")
maven_install(
    name = "maven",
    artifacts = JAVA_DEPENDENCIES,
    repositories = MAVEN_REPOS,
)
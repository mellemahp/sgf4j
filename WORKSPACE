workspace(name = "commentator")

#######################
# Base bazel tool imports
#######################
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

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

#######
# PMD #
#######
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

rules_pmd_version="0.2.0"
rules_pmd_sha="54b76b07b32c10886d50af52188a1410ec95e046f0425ad23c484e9d8de3b7f7"

http_archive(
    name = "rules_pmd",
    sha256 = rules_pmd_sha,
    strip_prefix = "bazel_rules_pmd-{v}".format(v = rules_pmd_version),
    url = "https://github.com/buildfoundation/bazel_rules_pmd/archive/v{v}.tar.gz".format(v = rules_pmd_version),
)

load("@rules_pmd//pmd:dependencies.bzl", "rules_pmd_dependencies")
rules_pmd_dependencies()

load("@rules_pmd//pmd:toolchains.bzl", "rules_pmd_toolchains")
rules_pmd_toolchains()

#############
# Test Deps #
#############
load("//:dependencies.bzl", "JAVA_TEST_DEPENDENCIES")
maven_install(
    name = "maven_test",
    artifacts = JAVA_TEST_DEPENDENCIES,
    repositories = MAVEN_REPOS,
)
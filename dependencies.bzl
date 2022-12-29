
"""
This module contains all of the dependencies installed via rules_jvm_external
"""

#####################
# JAVA DEPENDENCIES #
#####################
MAVEN_REPOS = [
    "https://repo1.maven.org/maven2",
]

NULLAWAY_VERSION = "0.10.2"
FINDBUGS_VERSION = "3.0.2"
SMITHY_VERSION = "1.26.4"

JAVA_DEPENDENCIES = [
    ###### PLUGIN/ANNOTATION PROCESSOR DEPS #####
    # nullaway
    "com.google.code.findbugs:jsr305:%s" % FINDBUGS_VERSION,
    "com.uber.nullaway:nullaway:%s" % NULLAWAY_VERSION,
    ####### codegen ###########
    "com.squareup:javapoet:1.13.0",
    # automatic service wiring
    "com.google.auto.service:auto-service:1.0.1",
    "com.google.auto.service:auto-service-annotations:1.0.1",
    ####### smithy ########
    "software.amazon.smithy:smithy-model:%s" % SMITHY_VERSION,
    ####### plugins ########
    "org.pf4j:pf4j:3.8.0"
]

#############################
#     TEST DEPENDENCIES     #
#############################

JUNIT_PLATFORM_VERSION = "1.9.1"
JUNIT_JUPITER_VERSION = "5.9.1"
MOCKITO_VERSION = "4.8.1"

JAVA_TEST_DEPENDENCIES = [
    "org.opentest4j:opentest4j:1.2.0",
    # Mockito
    "org.mockito:mockito-core:%s" % MOCKITO_VERSION,
    "org.mockito:mockito-junit-jupiter:%s" % MOCKITO_VERSION,
    # JUNIT dependencies
    "org.apiguardian:apiguardian-api:1.1.2",
    "org.junit.jupiter:junit-jupiter-api:%s"% JUNIT_JUPITER_VERSION,
    "org.junit.jupiter:junit-jupiter-engine:%s" % JUNIT_JUPITER_VERSION,
    "org.junit.jupiter:junit-jupiter-params:%s" % JUNIT_JUPITER_VERSION,
    "org.junit.platform:junit-platform-commons:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-console:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-engine:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-launcher:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-suite-api:%s" % JUNIT_PLATFORM_VERSION,
]
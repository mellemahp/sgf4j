
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
    ####### plugins #######
    'org.pf4j:pf4j:3.8.0',
    ####### logging #######
    "org.slf4j:slf4j-api:2.0.6",
    "ch.qos.logback:logback-classic:1.4.5"
]
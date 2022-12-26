JAVAC_OPTS = [
     # Sets nullaway errors to break build
     "-Xep:NullAway:ERROR",
     # sets packages for nullaway to run on
     "-XepOpt:NullAway:AnnotatedPackages=com.hmellema.sgf4j",
]
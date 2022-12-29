JAVAC_OPTS = [
    # set packages for nullaway to run on
    "-XepOpt:NullAway:AnnotatedPackages=com.hmellema.sgf4j",
    "-XepOpt:NullAway:CheckOptionalEmptiness=true",
    # Sets nullaway errors to break build
    "-Xep:NullAway:ERROR",
]
plugins {
    kfc("library")
    seskar()
    `react-query-declarations`
}

val reactQueryVersion = property("react-query.version") as String

dependencies {
    implementation(npm("@tanstack/react-query", reactQueryVersion))

    implementation(enforcedPlatform(kotlinwBom()))
    implementation(kotlinw("web"))
    implementation(kotlinw("react-core"))
}

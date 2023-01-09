plugins {
    kfc("library")
    seskar()
    `react-virtual-declarations`
}

val reactVirtualVersion = property("react-virtual.version") as String

dependencies {
    implementation(npm("@tanstack/react-virtual", reactVirtualVersion))

    implementation(enforcedPlatform(kotlinwBom()))
    implementation(kotlinw("browser"))
    implementation(kotlinw("react-core"))
}

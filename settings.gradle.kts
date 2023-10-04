pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SampleApp"
include(
    ":app",
    ":core:designsystem",
    ":core:data",
    ":core:domain",
    ":core:navigation",
    ":core:model",
    ":core:ui",
    ":core:testing",
    ":core:datastore",
    ":feature:main",
)

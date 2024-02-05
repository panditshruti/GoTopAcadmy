pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        gradlePluginPortal()
        jcenter()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        jcenter()
    }
}

rootProject.name = "GoTop Academy"
include(":app")
 
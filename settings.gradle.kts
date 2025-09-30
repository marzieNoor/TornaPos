pluginManagement {
    repositories {
        maven {
            setUrl("https://maven.myket.ir")
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            setUrl("https://maven.myket.ir")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "TornaPos"
include(":app")

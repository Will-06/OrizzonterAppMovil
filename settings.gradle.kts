import org.gradle.authentication.http.BasicAuthentication

pluginManagement {
    repositories {
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
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create("basic", BasicAuthentication::class.java)
            }
            credentials {
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_DOWNLOADS_TOKEN").orNull
                    ?: throw GradleException("La propiedad 'MAPBOX_DOWNLOADS_TOKEN' no está definida. Agrega -PMAPBOX_DOWNLOADS_TOKEN=tu_token o defínelo en gradle.properties.")
            }
        }
    }
}

rootProject.name = "OrizzonterApp"
include(":app")

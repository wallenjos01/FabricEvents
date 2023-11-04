import java.net.URI

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.loom)
}

group = "org.wallentines"

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

loom {
    runs {
        getByName("client") {
            runDir = "run/client"
            ideConfigGenerated(false)
            client()
        }
        getByName("server") {
            runDir = "run/server"
            ideConfigGenerated(false)
            server()
        }
    }
}

repositories {
    mavenCentral()
    maven("https://maven.wallentines.org/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots1"
        mavenContent { snapshotsOnly() }
    }
    mavenLocal()
}

dependencies {

    minecraft("com.mojang:minecraft:1.20.2")
    mappings(loom.officialMojangMappings())

    api("org.wallentines:midnightcfg-api:2.0.0-SNAPSHOT")
    api("org.wallentines:midnightlib:1.3.1-SNAPSHOT")

    modImplementation("net.fabricmc:fabric-loader:0.14.21")

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}

tasks.withType<ProcessResources> {

    filesMatching("fabric.mod.json") {
        expand(project.properties)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group as String
            version = version as String
            from(components["java"])
        }
    }

    repositories {
        if(project.hasProperty("pubUrl")) {
            maven {
                name = "pub"
                url = URI.create(project.properties["pubUrl"] as String)
                credentials(PasswordCredentials::class.java)
            }
        }
    }
}
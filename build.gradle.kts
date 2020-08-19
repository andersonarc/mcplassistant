import org.gradle.jvm.tasks.Jar

plugins {
    java
    kotlin("jvm") version "1.3.72"
}

group = "com.github.andersonarc"
version = "2.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    register<Jar>("buildJar") {
        dependsOn(configurations.compileClasspath)
        manifest {
            attributes(mapOf("Main-Class" to "com.github.andersonarc.mcsloader.MainKt"))
        }
        from(sourceSets.main.get().output)
        from(configurations.compileClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) })
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

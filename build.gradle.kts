plugins {
    java
}

group = "net.azisaba"
version = "1.1.3"

repositories {
    mavenCentral()

    // velocity repo
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    // velocity-api
    compileOnly("com.velocitypowered:velocity-api:4.2.1-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:4.2.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.0.2")

    // netty
    compileOnly("io.netty:netty-all:4.1.77.Final")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}

plugins {
    id("it.nicolasfarabegoli.conventional-commits") version "3.1.3"
    id("groovy")
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp.aggregation").version("1.1.0")
    id("io.micronaut.application") version "4.5.3"
    id("io.micronaut.aot") version "4.5.3"
    id("io.micronaut.library") version "4.5.3"
    id("io.micronaut.openapi") version "4.5.3"
    id("jacoco")
    id("org.sonarqube") version "latest.release"
}

version = project.properties["z4jVersion"]!!
val dataFakerVersion = project.properties["dataFakerVersion"]!!
val lombokVersion = project.properties["lombokVersion"]!!
group = "lol.pbu"

extra["netty.version"] = "4.1.124.Final"

configurations.create("lombok")

application {
    mainClass.set("lol.pbu.Application")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

sonarqube {
    properties {
        property("sonar.tests", "src/test/groovy")
    }
}

dependencies {
    implementation(platform("io.micronaut.platform:micronaut-platform:4.5.3"))
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.slf4j:jul-to-slf4j")
    implementation("io.micronaut.validation:micronaut-validation")
    "lombok"("org.projectlombok:lombok:${lombokVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("net.datafaker:datafaker:$dataFakerVersion")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17") // graalvm-ce
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<Javadoc>().configureEach {
    // This will generate an empty Javadoc JAR to satisfy publishing requirements
    // without failing the build on documentation errors from generated code.
    source = files().asFileTree
}


micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("lol.pbu.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
    openapi {
        version = "6.16.0"
        client(file("src/main/resources/z4j.yaml")) {
            apiPackageName.set("lol.pbu.z4j.client")
            modelPackageName.set("lol.pbu.z4j.model")
            useReactive.set(false)
            useAuth.set(false)
            lombok.set(true)
            clientId.set("zendesk")
            apiNameSuffix.set("Client")
            alwaysUseGenerateHttpResponse.set(true)
        }
    }
}
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it) {
            exclude("lol/pbu/Application.class")
        }
    }))
}
tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
tasks.check {
    dependsOn(tasks.jacocoTestReport)
}
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED)
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStackTraces = true
        showCauses = true
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set(project.name)
                description.set("A Java client for the Zendesk API, generated from OpenAPI specs.")
                url.set("https://github.com/pbu-lol/z4j")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("jonathan-zollinger")
                        name.set("Jonathan Zollinger")
                        email.set("jonathan.zollinger@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/pbu-lol/z4j.git")
                    developerConnection.set("scm:git:ssh://github.com/pbu-lol/z4j.git")
                    url.set("https://github.com/pbu-lol/z4j")
                }
            }
        }
    }
    repositories {
        maven {
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}

nmcpAggregation {
    centralPortal {
        username = System.getenv("SONATYPE_USERNAME")
        password = System.getenv("SONATYPE_PASSWORD")
        publishingType = "AUTOMATIC"
    }
    publishAllProjectsProbablyBreakingProjectIsolation()
}

/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED

plugins {
    id("it.nicolasfarabegoli.conventional-commits") version "3.1.3"
    id("groovy")
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp.aggregation").version("1.4.4")
    id("io.micronaut.library") version "4.6.2"
    id("jacoco")
    id("org.sonarqube") version "latest.release"
}

group = "lol.pbu"
version = project.properties["z4jVersion"]!!

val dataFakerVersion = project.properties["dataFakerVersion"]!!
val lombokVersion = project.properties["lombokVersion"]!!

configurations.create("lombok")

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    constraints {
        implementation("io.micronaut:micronaut-json-core:4.10.16") {
            because("CVE-2026-33012 & CVE-2026-33013")
        }
        implementation("io.micronaut:micronaut-context:4.10.22") {
            because("CVE-2026-44241")
        }
        implementation("org.mozilla.rhino:1.7.14.1") {
            because("CVE-2025-66453")
        }
    }
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("io.micronaut:micronaut-retry")
    "lombok"("org.projectlombok:lombok:${lombokVersion}")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("net.datafaker:datafaker:$dataFakerVersion")
    testImplementation("ch.qos.logback:logback-classic")
}

java {
    sourceCompatibility = JavaVersion.toVersion("21") // graalvm-ce
    withSourcesJar()
    withJavadocJar()
}

configurations.all {
    resolutionStrategy {
        force("io.netty:netty-bom:4.2.10.Final")
    }
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
}

sonarqube {
    properties {
        property("sonar.tests", "src/test/groovy")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
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
        events = setOf(FAILED)
        exceptionFormat = FULL
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
                description.set("A Java client for the Zendesk API.")
                url.set("https://github.com/PeanutButter-Unicorn/z4j")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("Jonathan-Zollinger")
                        name.set("Jonathan Zollinger")
                        email.set("jonathan.zollinger@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/PeanutButter-Unicorn/z4j")
                    developerConnection.set("scm:git:ssh://github.com/PeanutButter-Unicorn/z4j")
                    url.set("https://github.com/PeanutButter-Unicorn/z4j")
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


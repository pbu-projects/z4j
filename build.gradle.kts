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
    id("io.micronaut.library") version "5.0.0"
    // id("io.micronaut.openapi") version "5.0.0"
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
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("io.micronaut:micronaut-retry")
    "lombok"("org.projectlombok:lombok:${lombokVersion}")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("net.datafaker:datafaker:$dataFakerVersion")
    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.18.2")
    testImplementation("ch.qos.logback:logback-classic")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
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
    // openapi {
    //     version = "6.20.0"
    //     client(file("src/main/resources/Support.yaml")) {
    //         apiPackageName.set("lol.pbu.z4j.client")
    //         modelPackageName.set("lol.pbu.z4j.model")
    //         useReactive.set(true)
    //         useAuth.set(false)
    //         lombok.set(true)
    //         clientId.set("zendesk")
    //         apiNameSuffix.set("Client")
    //         alwaysUseGenerateHttpResponse.set(false)
    //         generateHttpResponseWhereRequired.set(false)
    //         additionalProperties.put("retryable", "true")
    //     }
    // }
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
    maxHeapSize = "2g"
    finalizedBy(tasks.jacocoTestReport)
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

val javaToolchains = project.extensions.getByType<JavaToolchainService>()

val generateTestFixtures by tasks.registering(JavaExec::class) {
    group = "verification"
    description = "Generates static test data fixtures (YAML files) using DataFaker."
    dependsOn(tasks.compileTestGroovy)
    javaLauncher.set(javaToolchains.launcherFor(java.toolchain))
    mainClass.set("lol.pbu.z4j.fixture.FixtureGenerator")
    classpath = sourceSets["test"].runtimeClasspath
}

val clientCoverageReport by tasks.registering(JavaExec::class) {
    group = "verification"
    description = "Scans all 99 @Client interfaces and Spock test specs to generate a role-based coverage matrix in src/test/README.md."
    dependsOn(tasks.compileTestGroovy)
    javaLauncher.set(javaToolchains.launcherFor(java.toolchain))
    mainClass.set("lol.pbu.z4j.coverage.ClientCoverageReporter")
    classpath = sourceSets["test"].runtimeClasspath
    args = listOf(project.rootDir.absolutePath, project.file("src/test/README.md").absolutePath)
}

tasks.withType<Test> {

    useJUnitPlatform()
    testLogging {
        events = setOf(FAILED)
        exceptionFormat = FULL
        showStackTraces = true
        showCauses = true
    }

    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) {
                logger.lifecycle("Test Results: ${result.resultType} (${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped, ${result.testCount} total)")
            }
        }
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
    })
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


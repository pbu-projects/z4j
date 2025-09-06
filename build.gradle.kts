plugins {
    id("it.nicolasfarabegoli.conventional-commits") version "3.1.3"
    id("groovy")
    id("io.micronaut.application") version "4.5.3"
    id("io.micronaut.aot") version "4.5.3"
    id("io.micronaut.library") version "4.5.3"
    id("io.micronaut.openapi") version "4.5.3"
}

version = project.properties["z4jVersion"]!!
val dataFakerVersion = project.properties["dataFakerVersion"]!!
group = "lol.pbu"

application {
    mainClass.set("lol.pbu.Application")
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    compileOnly("org.projectlombok:lombok")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.slf4j:jul-to-slf4j")
    implementation("io.micronaut.validation:micronaut-validation")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("net.datafaker:datafaker:$dataFakerVersion")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17") // graalvm-ce
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

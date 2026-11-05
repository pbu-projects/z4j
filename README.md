<img src="src/main/docs/z4j.svg" width="200" alt="z4j logo"> 

The cloud-native Java client for Zendesk.

## About The Project
Built with Micronaut, z4j focuses on low memory use, fast startup, and native GraalVM support.

## Runtime Performance

Compared to traditional reflection-based clients like CloudBees:

* **Zero reflection:** Serialization is handled at compile time without runtime reflection caches.
* **Low memory overhead:** Designed for lean execution in CLI tools and containerized services.

### Memory Benchmark (CloudBees vs z4j)

Here are the memory allocation results when parsing Zendesk tickets into domain models:

| Ticket Cohort | Payload Size | CloudBees (Jackson AST + Reflection) | z4j (Compile-Time Serde Records) | z4j Memory Savings |
|:---|:---|:---|:---|:---|
| **25 Tickets** | 43 KB JSON | 50,344 KB (cold start) | 24,401 KB (cold start) | **52% less memory** |
| **50 Tickets** | 86 KB JSON | 1,661 KB | 520 KB | **69% less memory** |
| **100 Tickets** | 173 KB JSON | 3,799 KB | 1,035 KB | **73% less memory** |

Run the benchmark locally:

```bash
./gradlew benchmark
```

### Installation

**Gradle**

| Gradle Kotlin                                  | Gradle                                        |
|:-----------------------------------------------|:----------------------------------------------|
| <pre>implementation("lol.pbu:z4j:0.4.0")</pre> | <pre>implementation 'lol.pbu:z4j:0.4.0'</pre> |

**Maven**

```xml
<dependency>
	<groupId>lol.pbu</groupId>
	<artifactId>z4j</artifactId>
	<version>0.4.0</version>
</dependency>
```

## Example Projects

- [zcmi] is a cli project built with z4j.

## Contributing

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=PeanutButter-Unicorn_z4j&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=PeanutButter-Unicorn_z4j)

Please read our [Code of Conduct](CODE_OF_CONDUCT.md) and [Contributing Guide](CONTRIBUTING.md) for details on our
development process, style guides, and testing strategy.

[Contributing Guide]:CONTRIBUTING.md#set-up-your-machine

[Micronaut]:https://graal.cloud/gdk/

[testing]:CONTRIBUTING.md#testing

[zcmi]:https://github.com/PeanutButter-Unicorn/zcmi

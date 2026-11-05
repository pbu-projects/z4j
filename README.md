<img src="src/main/docs/z4j.svg" width="200" alt="z4j logo"> 

The cloud-native Java client for Zendesk.

## About The Project
Built with Micronaut, z4j focuses on low memory use, fast startup, and native GraalVM support.

## Runtime Performance

Compared to traditional reflection-based clients like CloudBees:

* **Zero reflection:** Serialization is handled at compile time without runtime reflection caches.
* **Low memory overhead:** Designed for lean execution in CLI tools and containerized services.

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

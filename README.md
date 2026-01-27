<img src="src/main/docs/z4j.svg" width="200" alt="z4j logo"> 

A Java client for the Zendesk API

## About The Project

Z4j is a Java client for the Zendesk's APIs built for quick performance and enterprise grade applications.
Z4j gets its speed and durability from the [Micronaut] framework and thorough [testing].

## Getting Started

To get a local copy up and running, please follow the detailed setup instructions in our
[Contributing Guide].

### Installation

| Gradle Kotlin                                  | Gradle                                        |
|:-----------------------------------------------|:----------------------------------------------|
| <pre>implementation("lol.pbu:z4j:0.1.0")</pre> | <pre>implementation 'lol.pbu:z4j:0.1.0'</pre> |

**Maven**

```xml
<dependency>
	<groupId>lol.pbu</groupId>
	<artifactId>z4j</artifactId>
	<version>0.1.0</version>
</dependency>
```

## Example Projects

- [Zencli] is built with z4j and compiled to a native image with graalvm.

## Contributing

Please read our [Code of Conduct](CODE_OF_CONDUCT.md) and [Contributing Guide](CONTRIBUTING.md) for details on our
development process, style guides, and testing strategy.

[Contributing Guide]:CONTRIBUTING.md#set-up-your-machine

[Micronaut]:https://graal.cloud/gdk/

[testing]:CONTRIBUTING.md#testing

[Zencli]:https://github.com/PeanutButter-Unicorn/zencli
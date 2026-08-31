<img src="src/main/docs/z4j.svg" width="200" alt="z4j logo"> 

The ultra-lightweight, cloud-native Java client for the Zendesk API

## About The Project

A zippy Zendesk client built for heavy lifting, z4j pairs [Micronaut]’s lightning-fast architecture and minimal footprint with a [testing] suite so rigorous it’s borderline obsessive.

## Getting Started



### Installation

**Gradle**

| Gradle Kotlin                                  | Gradle                                        |
|:-----------------------------------------------|:----------------------------------------------|
| <pre>implementation("lol.pbu:z4j:0.2.1")</pre> | <pre>implementation 'lol.pbu:z4j:0.2.1'</pre> |

**Maven**

```xml
<dependency>
	<groupId>lol.pbu</groupId>
	<artifactId>z4j</artifactId>
	<version>0.2.1</version>
</dependency>
```

## Example Projects

- [zcmi] is a cli project built with z4j.

## Testing

Integration tests are partitioned into domain-specific tasks to prevent exceeding Zendesk Sandbox rate limits (HTTP 429). Run tasks individually and wait at least **1 minute** between executions:
- `./gradlew ticketingTest`
- `./gradlew userTest`
- `./gradlew helpCenterTest`
- `./gradlew adminTest`

For details on test coverage reporting and the project's current API testing status, please see [TESTING.md](TESTING.md).

## Contributing

Please read our [Code of Conduct](CODE_OF_CONDUCT.md) and [Contributing Guide] for details on our
development process, style guides, and [testing] strategy.

[Contributing Guide]:CONTRIBUTING.md#set-up-your-machine

[Micronaut]:https://graal.cloud/gdk/

[testing]:CONTRIBUTING.md#testing

[zcmi]:https://github.com/PeanutButter-Unicorn/zcmi

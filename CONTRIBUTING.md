# Contributing to z4j

By participating in this project, you agree to abide our
[code of conduct]


**✨ Thank you for contributing to z4j! ✨**

PBU projects are open to contributions! Below are some instructions on best practices and standards used when contributing to this project!

## Style Guide
- This project uses [google's java style guide].
- We follow (and enforce) [conventional commits] in this repo.
## Set up your machine

`z4j` is written in java 17, runs on [graal community distro], and uses [gradle] as its build tool.

### Prerequisites:
- Gradle doesn't need to be installed locally, a [gradle wrapper] is provided with this repo.
- Docker or Podman installed and running at compile time
- [Graal-CE 17]
- [Git]

#### Getting Started
Create your own fork of `z4j`, clone your fork and call the gradle wrapper to build the project

```shell
git clone git@github.com:my-user/z4j.git
cd z4j
./gradlew build
```
## IDE
Any IDE specific documentation will reference IntelliJ configured to [delegate build and run actions to gradle].

# Testing

### Prerequisites

> [!NOTE]
> Testing for z4j requires a live Zendesk instance. This ensures that the generated client is compliant with the actual
> Zendesk API behavior. We don't provide a dedicated zendesk instance, it's up to you to provide that.

To run the tests, you will need:

1. A Zendesk account with the Help Center activated, along with an API token for access.
2. Users with [different roles] created in your Zendesk instance.
3. [Environment variables] configured in your test environment

### Required Roles for Testing

You'll need to set up the following users in your Zendesk account:

* A **Help Center Admin**: This user has administrative privileges in the Help Center.
* An **Agent**: A standard agent user.
* An **End-user**: A standard customer/end-user.


<details id="user-configuration"><summary><strong>User Configuration</strong></summary>

View a user's configured role by navigating to {domain}.zendesk.com/admin/people/team/members, then selecting a user.
<table>
  <thead>
    <tr>
      <th>User Type</th>
      <th>Product</th>
      <th>Role</th>
      <th>Access</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td rowspan="5">Admin</td>
      <td>Support</td><td><strong>Admin</strong></td><td>✅</td>
    </tr>
    <tr><td>Guide</td><td><em>Defined by Support role</em></td><td>✅</td></tr>
    <tr><td>Explore</td><td><em>Defined by Support role</em></td><td>✅</td></tr>
    <tr><td>Talk</td><td><strong>Admin</strong></td><td>✅</td></tr>
    <tr><td>Chat</td><td><strong>Admin</strong></td><td>✅</td></tr>
    <tr>
      <td rowspan="5">Agent</td>
      <td>Support</td><td><strong>Agent</strong></td><td>✅</td>
    </tr>
    <tr><td>Guide</td><td><em>Defined by Support role</em></td><td>✅</td></tr>
    <tr><td>Explore</td><td><em>Defined by Support role</em></td><td>✅</td></tr>
    <tr><td>Talk</td><td><strong>Agent</strong></td><td>✅</td></tr>
    <tr><td>Chat</td><td><strong>Agent</strong></td><td>✅</td></tr>
    <tr>
      <td rowspan="5">End-user</td>
      <td>Support</td>
      <td rowspan="5" colspan="2" >Not Applicable
    </tr>
    <tr><td>Guide</td></tr>
    <tr><td>Explore</td></tr>
    <tr><td>Talk</td></tr>
    <tr><td>Chat</td></tr>
  </tbody>
</table>

</details>

### Required Environment Variables

After setting up your [users] and token, export the following environment variables:

* `Z4J_URL`: The FQDN / URL for your Zendesk instance.
* `Z4J_TOKEN` : The API token for your Zendesk instance.
* `Z4J_ADMIN_EMAIL`: The email address for your Help Center Admin user.
* `Z4J_AGENT_EMAIL`: The email address for your Agent user.
* `Z4J_END_USER_EMAIL`: The email address for your End-user.


> [!NOTE] use a `.env` file with these variables assigned on each line and save it in the root of this repo (don't track
> it in your git history). On windows it may be a bit tricky to dot-source an env file, but you can use this [source function] for similar behavior.

## Testing Strategy

One of the most important parts of contributing to z4j is getting tests right. A test is written adequately if:
- Tests don't take a lot of developer time to write.
- Tests don't overstep their bounds
- Tests are thorough and comprehensive.
  - Negative tests
  - e.g. behaviors between different auth level

### Testing Commandments
- Methods and features are to have [adequate] unit and integration tests written before any pull request can be accepted.
- Because we use lombok, we don't need to test setters and getters. Using getters and setters is the preferred way to access class fields.
- Features are to have [adequate] integration and end-to-end tests.
- Fixes are to have [adequate] unit, integration and end-to-end tests included with the fix for the sake of [regression testing]. Fixes should also document their fixes such that regression testing is properly captured. 
- Tests should only test one thing
  - e.g. `Set store location with zip code.`
  - e.g. `Fail to set store location using invalid zip code`
  - e.g. `Set store location by city name`

A good way to summarize the 'testing commandments' is "If it's documented, [test it]!". nothing more and nothing less. See [google's styleguide] on documentation for more information. Tests should also be uncomplicated and readable. To that end, z4j uses data driven testing that so that we write one test that covers a gamut of documentation. 

### Example

```java
/**
 * {@summary List Locales}
 * Lists the translation locales available for the account.  Allowed for anyone
 *
 * @return Success response (status code 200)
 */
@Get("/api/v2/locales")
HttpResponse<@Valid LocalesResponse> listLocales();
```

Given the method above and its documentation, we should expect tests to check the following boxes

- valid usage returns a 200 response
- valid usage returns a valid locale object
- invalid usage returns something OTHER than a 200 response (it'd be good to improve the docs to iterate expected failure responses as well, but if it's not documented, don't test for it. In this case, this method will only fail when it can't hit the server at all)
- validate the above behavior across all expected user privileges

With these requirements, we use spock's data driven testing to provide the following tests:

```groovy
    @Unroll
    def "can list public locales for #clientName"() {
        when:
        def response = localesClient.listLocales()

        then:
        response.status() == HttpStatus.OK

        where:
        clientName              | localesClient
        "managers client"       | managersLocalesClient
        "agents client"         | agentsLocalesClient
        "users client"          | usersLocalesClient
        "no-auth client"        | noAuthLocalesClient
        "bad email client"      | badEmailLocalesClient
    }

    def "calling locales client with a bad URL throws an exception"() {
        when:
        badUrlLocalesClient.listLocales()

        then:
        thrown(HttpClientException)
    }
```
This method is a little tricky to test for negative tests because the only way to get it to fail is to provide it a bad url, otherwise this endpoint will give you the locales. 

[adequate]:#Testing-Strategy
[branches of code]:https://medium.com/@zubairkhansh/branch-testing-and-branch-coverage-3fb4bbd9f949
[code of conduct]:CODE_OF_CONDUCT.md
[conventional commits]:https://www.conventionalcommits.org/en/v1.0.0/
[delegate build and run actions to gradle]:https://www.jetbrains.com/help/idea/work-with-gradle-projects.html#delegate_build_gradle
[different roles]:#Required-Roles-for-Testing
[google's java style guide]:https://google.github.io/styleguide/javaguide.html
[google's styleguide]:https://google.github.io/styleguide/javaguide.html#s7-javadoc
[gradle]:https://gradle.org/maven-and-gradle/
[gradle wrapper]:https://docs.gradle.org/current/userguide/gradle_wrapper_basics.html
[Git]:https://gist.github.com/Jonathan-Zollinger/8d9a231a57f3d33ff813989c34df00e0
[graal community distro]:https://www.graalvm.org/release-notes/JDK_17/
[Graal-CE 17]:https://www.graalvm.org/jdk17/docs/
[Environment Variables]:#Required-Environment-Variables
[source function]:https://gist.github.com/Jonathan-Zollinger/96160f971741f5f3a8749d10127e7764
[test it]:https://www.geeksforgeeks.org/software-engineering/difference-between-positive-testing-and-negative-testing/
[Testing]:#testing
[users]:#Required-Roles-for-Testing
# Baseline Guidelines & Context for AI Contributors

Welcome to `z4j`! This document establishes baseline context, coding standards, and testing policies for all AI agents and automated tools contributing to this repository.

---

## 1. Project Overview & Architecture

- **Description**: `z4j` is an open-source Java 21 client for the Zendesk API.
- **Tech Stack**:
  - **Language**: Java 21 (GraalVM CE) for main source code, Groovy for Spock 2 test specifications.
  - **Framework**: Micronaut 4 (`io.micronaut.library`).
  - **Build Tool**: Gradle (via `./gradlew` wrapper).
  - **API Definition**: OpenAPI Spec 2.0/3.0 located at [`src/main/resources/z4j.yaml`](file:///home/jimmy/git/pbu/z4j/src/main/resources/z4j.yaml).
- **Code Generation**:
  - Endpoint implementations and model classes rely on OpenAPI Generator driven by `z4j.yaml`.
  - When modifying or extending API coverage, inspect and update `z4j.yaml` first.

---

## 2. Testing Standards

> [!IMPORTANT]
> **No Mocking Policy for Sandbox-Testable Functionality**
> - **Do NOT mock** HTTP calls, services, or API endpoints if they can be tested against a live sandbox/test environment (which applies to almost all features in this client).
> - All integration tests must validate real behavior against a live Zendesk instance to ensure actual Zendesk API compliance.
> - Mocking is strictly restricted to isolated unit logic that has no remote dependency or sandbox equivalent.

- **Test Framework**: Spock 2 specifications written in Groovy under [`src/test/groovy`](file:///home/jimmy/git/pbu/z4j/src/test/groovy).
- **Test Data**: Use [DataFaker](file:///home/jimmy/git/pbu/z4j/build.gradle.kts#L65) (`net.datafaker:datafaker`) for dynamic test payload generation instead of static hardcoded values.
- **Environment Prerequisites**: Tests rely on environment configuration (e.g. `.env`) and pre-configured Zendesk roles (Admin, Agent, End-user). Refer to [`CONTRIBUTING.md`](file:///home/jimmy/git/pbu/z4j/CONTRIBUTING.md#L36) for setup details.

---

## 3. Development & Workflow Rules

- **Code Style**: Adhere to the Google Java Style Guide.
- **Commit Messages**: Follow [Conventional Commits](file:///home/jimmy/git/pbu/z4j/build.gradle.kts#L20) format (`feat:`, `fix:`, `docs:`, `test:`, `refactor:`, `chore:`).
- **Verification**:
  - Always execute verification using `./gradlew check` or `./gradlew test` before marking work as complete.
  - Fix any failures at the root cause; do not swallow errors or weaken assertions to pass builds.

---

## 4. Expanding this Document

Future contributors (both humans and AI) should update this file whenever new baseline conventions, environment requirements, or architectural decisions are established.

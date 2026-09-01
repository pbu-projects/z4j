# z4j Integration Test Suite

This directory contains the Spock 2 integration test suite for the `z4j` Zendesk client. All tests run against the live Zendesk API sandbox.

## Test Fixtures & Data

Instead of generating dynamic payload data (e.g. faker names, descriptions, titles) in-memory during every test run, which balloons memory and causes Garbage Collection overhead, we use pre-computed static YAML fixtures.

The fixtures are located in `src/test/resources/fixtures/`:
- `article_fixtures.yaml`
- `category_fixtures.yaml`
- `ticket_fixtures.yaml`
- `user_segment_fixtures.yaml`

### Regenerating Fixtures
If you need to update the payload structures or add new mock data, do not edit the YAML files manually! You can regenerate them using the custom Gradle task:

```bash
./gradlew generateTestFixtures
```

This task uses DataFaker to predictably rebuild the static YAML files, which are then parsed by SnakeYAML during the Spock execution via `new Yaml().load(...)`.

## Coverage Reports
The test coverage matrix mapping client endpoints to Spock specs and Zendesk Roles (Admin, Agent, End User) has been moved to [COVERAGE.md](COVERAGE.md). You can regenerate the coverage matrix using `./gradlew clientCoverageReport`.

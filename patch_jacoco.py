import re

with open("build.gradle.kts", "r") as f:
    content = f.read()

content = content.replace(
    "executionData(tasks.withType<Test>())",
    "executionData.setFrom(fileTree(layout.buildDirectory).include(\"jacoco/*.exec\"))"
)

with open("build.gradle.kts", "w") as f:
    f.write(content)

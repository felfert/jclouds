[![javadoc](https://javadoc.io/badge2/com.github.felfert.jclouds/jclouds/javadoc.svg)](https://javadoc.io/doc/com.github.felfert.jclouds/jclouds) ![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/felfert/jclouds/ci.yaml)

# jclouds

Apache jclouds was an open source multi-cloud toolkit for the Java platform that gives you the freedom to create applications that are portable across clouds while giving you full control to use cloud-specific features.

For more information about the retirement of the project, visit https://attic.apache.org/projects/jclouds.html.

## License
Copyright (C) 2009-2022 The Apache Software Foundation

Licensed under the Apache License, Version 2.0

## This fork

is intended for usage in jenkins jclouds-plugin. Therefore, it is focused on compatibility to jenkins.
For example: this fork uses guice-6.0.0 instead of 7.0.0. Since apache's JIRA is now readonly, this
repo has enabled its own github issue tracker. For detailed changes see the [CHANGELOG](CHANGELOG.md).

## Usage with maven

Obviously, the groupId `org.apache.jclouds` could not be kept. The new groupId is `com.github.felfert.jclouds`.
So to use this new version with maven, use a snippet like this in your pom.xml:
```xml
    <dependency>
      <groupId>com.github.felfert.jclouds</groupId>
      <artifactId>jclouds-allcompute</artifactId>
      <version>2.7.4</version>
    </dependency>
```

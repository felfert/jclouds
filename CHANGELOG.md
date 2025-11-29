### Version 2.7.5
- Replace bcprov-ext-jdk18on 1.78 by bcprov-jdk18on 1.81
- Bunp shrinkwrap-depchain to 1.2.6
- Bump up snakeyaml to 2.5
- Bump up okhttp to 4.12.0
- Bump up okio to 3.16.3
- Bump up actions/setup-java to 5
- Bump up actions/checkout to 5
- Bump up build-helper-maven-plugin to 3.6.1
### Version 2.7.4
- Bump up gson to 2.13.1 to match jenkins
- Bump up maven-jar-plugin to 3.4.2
- Bump up duplicate-finder-maven-plugin to 2.0.1
- Bump up maven-javadoc-plugin to 3.3.1
- Bump up maven-resources-plugin to 3.3.1
- Bump up github actions checkout to 4
- Add separate CHANGELOG
- Activcate dependabot
### Version 2.7.3
- Add aggregated javadoc jar
- Fixed overlooked change of groupId in resource
### Version 2.7.2
- Updated issueManagement and project URL
- Removed upstream mailing list info
### Version 2.7.1
- Fixed digitalocean2 provider producing a NumberFormatException.
- Fixed JCLOUDS-1646 (by Andrew Gaul)
- Add EU_CENTRAL_2, EU_SOUTH_1, and EU_SOUTH_2 in AWS (by Andrew Gaul)
- Fix NPE on failure to parse AWSError when determing whether to retry request (by Mark Smiley)
- Switch guice-7.0.0 back to guice-6.0.0

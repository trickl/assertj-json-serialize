# Trickl AssertJ JSON Serialize
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.trickl/assertj-json-serialize/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.trickl/assertj-json-serialize)
[![build_status](https://travis-ci.com/trickl/assertj-json-serialize.svg?branch=master)](https://travis-ci.com/trickl/assertj-json-serialize)
[![Maintainability](https://api.codeclimate.com/v1/badges/1c360892853bbf2a61d5/maintainability)](https://codeclimate.com/github/trickl/assertj-json-serialize/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/1c360892853bbf2a61d5/test_coverage)](https://codeclimate.com/github/trickl/assertj-json-serialize/test_coverage)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

AssertJ fluent assertions for the serialization and deserialization of POJOs into JSON.

Installation
============

To install from Maven Central:

```xml
<dependency>
  <groupId>com.github.trickl</groupId>
  <artifactId>assertj-json-serialize</artifactId>
  <version>0.1.0</version>
</dependency>
```

## Example

```
    assertThat(account)
        .deserializesAsExpected()
        .serializesAsExpected()
        .schemaAsExpected();
```

### Building

To download the library into a folder called "assertj-json-serialize" run

```
git clone https://github.com/trickl/assertj-json-serialize.git
```

To build the library run

```
mvn clean build
```

## Acknowledgments

AssertJ - http://joel-costigliola.github.io/assertj/

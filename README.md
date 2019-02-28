# Trickl AssertJ JSON Serialize

[![build_status](https://travis-ci.com/trickl/assertj-json-serialize.svg?branch=master)](https://travis-ci.com/trickl/assertj-json-serialize)
[![Maintainability](https://api.codeclimate.com/v1/badges/1f66926c8f391be20ad4/maintainability)](https://codeclimate.com/github/trickl/assertj-json-serialize/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/1f66926c8f391be20ad4/test_coverage)](https://codeclimate.com/github/trickl/assertj-json-serialize/test_coverage)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

AssertJ fluent assertions for the serialization and deserialization of POJOs into JSON.

### Prerequisites

Requires Maven and a Java 8 compiler installed on your system.

## Example

```
    assertThat(account)
        .deserializesAsExpected()
        .serializesAsExpected()
        .schemaAsExpected();
```

### Installing

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

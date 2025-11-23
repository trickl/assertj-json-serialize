package com.trickl.assertj.examples;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NestedExample {
  @JsonProperty("first-example")
  private ExampleObject firstExample;

  @JsonProperty("second-example")
  private ExampleObject secondExample;

  public ExampleObject getFirstExample() {
    return firstExample;
  }

  public void setFirstExample(ExampleObject firstExample) {
    this.firstExample = firstExample;
  }

  public ExampleObject getSecondExample() {
    return secondExample;
  }

  public void setSecondExample(ExampleObject secondExample) {
    this.secondExample = secondExample;
  }
}

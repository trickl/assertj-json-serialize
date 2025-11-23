package com.trickl.assertj.examples;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class NestedComplexExample {
  @JsonProperty("list-example")
  private List<ExampleObject> firstExample;

  @JsonProperty("map-example")
  private Map<String, ExampleObject> secondExample;

  public List<ExampleObject> getFirstExample() {
    return firstExample;
  }

  public void setFirstExample(List<ExampleObject> firstExample) {
    this.firstExample = firstExample;
  }

  public Map<String, ExampleObject> getSecondExample() {
    return secondExample;
  }

  public void setSecondExample(Map<String, ExampleObject> secondExample) {
    this.secondExample = secondExample;
  }
}

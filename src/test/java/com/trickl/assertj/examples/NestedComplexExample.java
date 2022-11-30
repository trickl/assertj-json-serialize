package com.trickl.assertj.examples;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class NestedComplexExample {
  @JsonProperty("list-example")    
  private List<ExampleObject> firstExample;

  @JsonProperty("map-example")
  private Map<String, ExampleObject> secondExample;
}

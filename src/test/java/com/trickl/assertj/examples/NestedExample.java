package com.trickl.assertj.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NestedExample {
  @JsonProperty("first-example")    
  private Example firstExample;

  @JsonProperty("second-example")
  private Example secondExample;
}

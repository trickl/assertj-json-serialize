package com.trickl.assertj.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
public class Example {
    @JsonProperty("my-field")
    @JsonPropertyDescription("Description of my field")
    private String myField;
}

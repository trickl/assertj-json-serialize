package com.trickl.assertj.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class ExampleObject {
  @JsonProperty("my-field")
  @JsonPropertyDescription("Description of my field")
  private String myField;

  public String getMyField() {
    return myField;
  }

  public void setMyField(String myField) {
    this.myField = myField;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExampleObject that = (ExampleObject) o;
    return myField != null ? myField.equals(that.myField) : that.myField == null;
  }

  @Override
  public int hashCode() {
    return myField != null ? myField.hashCode() : 0;
  }
}

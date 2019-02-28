package com.trickl.assertj.core.api;

import com.trickl.assertj.core.api.json.serialize.JsonObject;
import com.trickl.assertj.core.api.json.serialize.JsonObjectAssert;

/** The entry point for all JSON assertions. */
public class JsonObjectAssertions {

  public static JsonObjectAssert assertThat(Object object) {
    return new JsonObjectAssert(new JsonObject(object));
  }
  
  /** Creates a new <code>{@link Assertions}</code>. */
  protected JsonObjectAssertions() {
    // empty
  }
}

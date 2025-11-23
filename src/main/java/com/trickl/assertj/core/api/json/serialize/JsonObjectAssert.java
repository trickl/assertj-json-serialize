package com.trickl.assertj.core.api.json.serialize;

/** Fluent JSON serialization assertions for a {@link JsonObject} under test. */
public class JsonObjectAssert extends AbstractJsonObjectAssert<JsonObjectAssert> {
  public JsonObjectAssert(JsonObject object) {
    super(object, JsonObjectAssert.class);
  }
}

package com.trickl.assertj.core.api.json.serialize;

import com.trickl.assertj.core.api.json.JsonAssert;
import com.trickl.assertj.examples.Example;
import org.assertj.core.api.BaseTestTemplate;

/**
 * Tests for <code>{@link JsonAssert}</code>.
 */
public abstract class JsonObjectAssertBaseTest extends BaseTestTemplate<JsonObjectAssert, JsonObject> {
  
  @Override
  protected JsonObjectAssert create_assertions() {
    Example example = new Example();
    example.setMyField("abc");
    return new JsonObjectAssert(new JsonObject(example));
  }

  @Override
  protected void inject_internal_objects() {
    super.inject_internal_objects();
  }
}

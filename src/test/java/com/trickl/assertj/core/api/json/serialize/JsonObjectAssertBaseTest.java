package com.trickl.assertj.core.api.json.serialize;

import com.trickl.assertj.core.api.json.JsonAssert;
import org.assertj.core.api.BaseTestTemplate;

/**
 * Tests for <code>{@link JsonAssert}</code>.
 */
public abstract class JsonObjectAssertBaseTest extends BaseTestTemplate<JsonObjectAssert, JsonObject> {
  
  @Override
  protected JsonObjectAssert create_assertions() {
    return new JsonObjectAssert(new JsonObject("abc"));
  }

  @Override
  protected void inject_internal_objects() {
    super.inject_internal_objects();
  }
}

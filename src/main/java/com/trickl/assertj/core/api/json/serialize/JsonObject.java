package com.trickl.assertj.core.api.json.serialize;

/** Simple wrapper that records the object under test for JSON assertions. */
public record JsonObject(Object object) {

  /**
   * Retains the legacy accessor signature so existing code can still call {@code getObject()}.
   *
   * @return the wrapped object under test
   */
  public Object getObject() {
    return object;
  }
}

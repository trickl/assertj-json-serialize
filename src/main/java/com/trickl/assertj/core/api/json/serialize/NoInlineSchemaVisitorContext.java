package com.trickl.assertj.core.api.json.serialize;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;

public class NoInlineSchemaVisitorContext extends VisitorContext {
  @Override
  public String getSeenSchemaUri(JavaType javaType) {
    if (javaType != null && !javaType.isPrimitive()) {
      return javaTypeToUrn(javaType);
    }
    return null;
  }
}
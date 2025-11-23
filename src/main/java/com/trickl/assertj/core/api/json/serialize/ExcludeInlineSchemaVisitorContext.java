package com.trickl.assertj.core.api.json.serialize;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import java.util.List;

/**
 * Visitor context that prevents Jackson from inlining schemas for specific packages so their
 * definitions stay referenced by URI instead.
 */
public class ExcludeInlineSchemaVisitorContext extends VisitorContext {
  private final List<String> excludePackages;

  public ExcludeInlineSchemaVisitorContext(List<String> excludePackages) {
    this.excludePackages = excludePackages;
  }

  @Override
  public String getSeenSchemaUri(JavaType javaType) {
    if (javaType != null && !javaType.isPrimitive() && isInExcludedPackage(javaType)) {
      return javaTypeToUrn(javaType);
    }
    return null;
  }

  private boolean isInExcludedPackage(JavaType javaType) {
    String packageName = javaType.getRawClass().getPackage().getName();
    return excludePackages.stream()
        .anyMatch(
            (exclude -> packageName.equals(exclude) || packageName.startsWith(exclude + ".")));
  }
}

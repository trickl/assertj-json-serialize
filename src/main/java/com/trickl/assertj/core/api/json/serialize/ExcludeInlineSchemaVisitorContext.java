package com.trickl.assertj.core.api.json.serialize;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcludeInlineSchemaVisitorContext extends VisitorContext {
  private final List<String> excludePackages;

  @Override
  public String getSeenSchemaUri(JavaType javaType) {
    if (javaType != null && !javaType.isPrimitive() && isInExcludedPackage(javaType)) {
      return javaTypeToUrn(javaType);
    }
    return null;
  }

  private boolean isInExcludedPackage(JavaType javaType) {
    String packageName = javaType.getRawClass().getPackage().getName();
    return excludePackages.stream().anyMatch((exclude -> 
      packageName.equals(exclude) || packageName.startsWith(exclude + ".")
    ));
  }
}
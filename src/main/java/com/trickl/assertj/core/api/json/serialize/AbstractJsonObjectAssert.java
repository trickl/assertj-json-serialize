package com.trickl.assertj.core.api.json.serialize;

import static com.trickl.assertj.core.api.JsonAssertions.json;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.trickl.assertj.core.api.json.JsonContainer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.assertj.core.api.AbstractAssert;

/**
 * Base class for all json related assertions on objects.
 *
 * @param <S> the "self" type of this assertion class. Please read &quot;<a
 *     href="http://bit.ly/1IZIRcY" target="_blank">Emulating 'self types' using Java Generics to
 *     simplify fluent API implementation</a>&quot; for more details.
 */
public abstract class AbstractJsonObjectAssert<S extends AbstractJsonObjectAssert<S>>
    extends AbstractAssert<S, JsonObject> {

  private ObjectMapper objectMapper = new ObjectMapper();

  private Path schemaResourcePath = null;

  private Path serializationResourcePath = null;

  private URL deserializationResourceUrl = null;

  public AbstractJsonObjectAssert(JsonObject actual, Class<?> selfType) {
    super(actual, selfType);
  }

  /**
   * Check the json serialization of the object matches the expected output.
   *
   * @return A new assertion object
   * @throws IOException If any file errors occur
   */
  public S serializationAsExpected() {
    if (serializationResourcePath == null) {
      serializationResourcePath =
          classAsResourcePathConvention(actual.getObject().getClass(), ".example.json");
    }

    String jsonString = serialize(actual.getObject());
    com.trickl.assertj.core.api.JsonAssertions.assertThat(json(jsonString))
        .allowingAnyArrayOrdering()
        .writeActualToFileOnFailure()
        .isSameJsonAs(safeJson(serializationResourcePath));
    return myself;
  }

  /**
   * Check the json deserialization of the object matches the expected output.
   *
   * @return A new assertion object
   * @throws IOException If any file errors occur
   */
  public S deserializationAsExpected() {
    if (deserializationResourceUrl == null) {
      deserializationResourceUrl =
          classAsResourceUrlConvention(actual.getObject().getClass(), ".example.json");
    }

    assertThat(deserialize(deserializationResourceUrl, actual.getObject().getClass()))
        .isEqualTo(actual.getObject());
    return myself;
  }

  /**
   * Check the json schema of the object matches the expected output.
   *
   * @return A new assertion object
   * @throws IOException If any file errors occur
   */
  public S schemaAsExpected() {
    if (schemaResourcePath == null) {
      schemaResourcePath =
          classAsResourcePathConvention(actual.getObject().getClass(), ".schema.json");
    }

    String jsonSchema = schema(actual);
    com.trickl.assertj.core.api.JsonAssertions.assertThat(json(jsonSchema))
        .allowingAnyArrayOrdering()
        .writeActualToFileOnFailure()
        .isSameJsonAs(safeJson(schemaResourcePath));
    return myself;
  }

  public S usingSchemaResourcePath(Path path) {
    schemaResourcePath = path;
    return myself;
  }

  public S usingSerializationResourcePath(Path path) {
    serializationResourcePath = path;
    return myself;
  }

  public S usingDeserializationResourceUrl(URL url) {
    deserializationResourceUrl = url;
    return myself;
  }

  public S usingObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    return myself;
  }

  private <T> T deserialize(URL value, Class<T> clazz) {
    try {
      return objectMapper.readValue(value, (Class<T>) clazz);
    } catch (IOException e) {
      throw new UncheckedIOException("Unable to deserialize JSON", e);
    }
  }

  private String serialize(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (IOException e) {
      throw new UncheckedIOException("Unable to serialize JSON", e);
    }
  }

  private String schema(Object obj) {
    try {
      JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(objectMapper);
      JsonSchema schema = schemaGen.generateSchema(actual.getObject().getClass());
      return objectMapper.writeValueAsString(schema);
    } catch (IOException e) {
      throw new UncheckedIOException("Unable to generate JSON schema", e);
    }
  }

  private JsonContainer safeJson(Path path) {
    try {
      return json(path);
    } catch (IOException e) {
      throw new UncheckedIOException("Unable to read path", e);
    }
  }

  private URL classAsResourceUrlConvention(Class clazz, String extension) {
    String resourceName = clazz.getSimpleName() + extension;
    return clazz.getResource(resourceName);
  }

  private Path classAsResourcePathConvention(Class clazz, String extension) {
    String resourcePath = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
    String projectDir = resourcePath.substring(0, resourcePath.indexOf("target"));
    return Paths.get(
        projectDir,
        "src/test/resources/",
        clazz.getPackage().getName().replaceAll("\\.", "/"),
        clazz.getSimpleName() + extension);
  }
}

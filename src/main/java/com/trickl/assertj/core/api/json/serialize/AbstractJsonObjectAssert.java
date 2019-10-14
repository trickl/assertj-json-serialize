package com.trickl.assertj.core.api.json.serialize;

import static com.trickl.assertj.core.api.JsonAssertions.json;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.trickl.assertj.core.api.json.JsonContainer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;

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

  private static final String DEFAULT_JSON_DATA_FILE_EXT = ".example.json";

  private static final String DEFAULT_SCHEMA_FILE_EXT = ".schema.json";

  private ObjectMapper objectMapper = new ObjectMapper();

  private Path schemaResourcePath = null;

  private Path serializationResourcePath = null;

  private URL deserializationResourceUrl = null;
  
  private boolean createExpectedIfAbsent = true;

  private String projectDir = null;

  private List<String> excludeInlineSchemaPackages = new ArrayList<>();

  private String jsonDataFileExtension = DEFAULT_JSON_DATA_FILE_EXT;

  private String schemaFileExtension = DEFAULT_SCHEMA_FILE_EXT;

  public AbstractJsonObjectAssert(JsonObject actual, Class<?> selfType) {
    super(actual, selfType);
  }

  /**
   * Check the json serialization of the object matches the expected output.
   *
   * @return A new assertion object
   */
  public S serializesAsExpected() {
    if (serializationResourcePath == null) {
      serializationResourcePath =
          classAsResourcePathConvention(actual.getObject().getClass(), jsonDataFileExtension);
    }
    
    Path actualPath = null;
    if (createExpectedIfAbsent && createEmptyJsonIfMissing(serializationResourcePath)) {
      actualPath = serializationResourcePath;
    }
    assertThat(serializationResourcePath).exists();

    String jsonString = serialize(actual.getObject());
    com.trickl.assertj.core.api.JsonAssertions.assertThat(json(jsonString))
        .allowingAnyArrayOrdering()        
        .writeActualToFileOnFailure(actualPath)
        .isSameJsonAs(safeJson(serializationResourcePath));
    return myself;
  }

  /**
   * Check the json deserialization of the object matches the expected output.
   *
   * @return A new assertion object
   */
  public S deserializesAsExpected() {
    if (deserializationResourceUrl == null) {
      deserializationResourceUrl =
          classAsResourceUrlConvention(actual.getObject().getClass(), jsonDataFileExtension);
    }

    assertThat(deserialize(deserializationResourceUrl, actual.getObject().getClass()))
        .isEqualTo(actual.getObject());
    return myself;
  }
  
  /**
   * Check the json deserialization of the object creates a non null object without error.
   *
   * @return A new assertion object
   */
  public S deserializesWithoutError() {
    if (deserializationResourceUrl == null) {
      deserializationResourceUrl =
          classAsResourceUrlConvention(actual.getObject().getClass(), jsonDataFileExtension);
    }

    assertThat(deserialize(deserializationResourceUrl, actual.getObject().getClass()))
        .isNotNull();
    return myself;
  }

  /**
   * Check the json schema of the object matches the expected output.
   *
   * @return A new assertion object
   */
  public S schemaAsExpected() {
    if (schemaResourcePath == null) {
      schemaResourcePath =
          classAsResourcePathConvention(actual.getObject().getClass(), 
          schemaFileExtension);
    }
    
    Path actualPath = null;
    if (createExpectedIfAbsent && createEmptyJsonIfMissing(schemaResourcePath)) {
      actualPath = schemaResourcePath;
    }
    assertThat(schemaResourcePath).exists();

    String jsonSchema = schema(actual);
    com.trickl.assertj.core.api.JsonAssertions.assertThat(json(jsonSchema))
        .allowingAnyArrayOrdering()
        .writeActualToFileOnFailure(actualPath)
        .isSameJsonAs(safeJson(schemaResourcePath));
    return myself;
  }
  
  private boolean createEmptyJsonIfMissing(Path path) {
    if (!path.toFile().exists()) {
      try {
        path.getParent().toFile().mkdirs();
        path.toFile().createNewFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()))) {
          bw.write("{}");
        }   
        return true;
      } catch (IOException e) {
        throw new UncheckedIOException("Could not create missing expected file", e);
      }      
    }
    return false;
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

  public S usingProjectDirectory(String projectDir) {
    this.projectDir = projectDir;
    return myself;
  }
  
  public S doNotCreateExpectedIfAbsent() {
    createExpectedIfAbsent = false;
    return myself;
  }

  public S excludeInlineSchemaPackage(String packageName) {
    excludeInlineSchemaPackages.add(packageName);
    return myself;
  }

  public S withJsonDataFileExtension(String extension) {
    jsonDataFileExtension = extension;
    return myself;
  }

  public S withSchemaFileExtension(String extension) {
    schemaFileExtension = extension;
    return myself;
  }

  protected <T> T deserialize(URL value, Class<T> clazz) {
    try {
      return objectMapper.readValue(value, (Class<T>) clazz);
    } catch (IOException e) {
      throw new UncheckedIOException("Unable to deserialize JSON", e);
    }
  }

  protected String serialize(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (IOException e) {
      throw new UncheckedIOException("Unable to serialize JSON", e);
    }
  }

  protected String schema(Object obj) {
    try {
      SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
      if (excludeInlineSchemaPackages.size() > 0) {
        visitor.setVisitorContext(
            new ExcludeInlineSchemaVisitorContext(excludeInlineSchemaPackages));
      }
      objectMapper.acceptJsonFormatVisitor(actual.getObject().getClass(), visitor);
      JsonSchema schema = visitor.finalSchema();
      return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);

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

  protected URL classAsResourceUrlConvention(Class<?> clazz, String extension) {
    String resourceName = clazz.getSimpleName() + extension;
    return clazz.getResource(resourceName);
  }

  protected Path classAsResourcePathConvention(Class<?> clazz, String extension) {
    String projectDirectory = projectDir;
    if (projectDirectory == null) {
      projectDirectory = getProjectDirectoryFromLocalClazz(clazz);
    }
    if (projectDirectory == null) {
      throw new RuntimeException(
          "Project directory must be set explicity when using a non-local class.");
    }    
    return Paths.get(
        projectDirectory,
        "src/test/resources/",
        clazz.getPackage().getName().replaceAll("\\.", "/"),
        clazz.getSimpleName() + extension);
  }

  private String getProjectDirectoryFromLocalClazz(Class<?> clazz) {
    CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
    if (codeSource != null) {
      String resourcePath = codeSource.getLocation().getPath();
      if (resourcePath.indexOf("target") >= 0) {
        return resourcePath.substring(0, resourcePath.indexOf("target"));
      }
    }
    return null;
  }
}

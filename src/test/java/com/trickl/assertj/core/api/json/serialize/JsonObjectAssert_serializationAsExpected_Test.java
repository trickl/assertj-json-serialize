package com.trickl.assertj.core.api.json.serialize;

import com.trickl.assertj.core.api.json.JsonAssert;
import static com.trickl.assertj.core.api.JsonObjectAssertions.assertThat;

import java.util.AbstractMap;
import java.util.Map;

import com.trickl.assertj.examples.Example;
import org.junit.Test;

/**
 * Tests for <code>{@link JsonAssert#isSameJsonAs(java.io.File)}</code>.
 * 
 * @author Yvonne Wang
 */
public class JsonObjectAssert_serializationAsExpected_Test extends JsonObjectAssertBaseTest {

  @Override
  protected JsonObjectAssert invoke_api_method() {
    return assertions.serializesAsExpected();
  }

  @Override
  protected void verify_internal_effects() {
  }
  
  @Test
  public void should_pass_on_deserialization_with_error() {         
    Example example = new Example();
    assertThat(example)
        .deserializesWithoutError();     
  }
  
  @Test
  public void should_pass_on_deserialization_equality() {         
    Example example = new Example();
    example.setMyField("abc");
    assertThat(example)
        .deserializesAsExpected();     
  }
      
  @Test
  public void should_pass_on_serialization_match() {         
    Example example = new Example();
    example.setMyField("abc");
    assertThat(example)
        .serializesAsExpected();     
  }

  @Test
  public void should_allow_explicit_project_directory() {    
    Example example = new Example();
    String testPath = this.getClass().getProtectionDomain()
        .getCodeSource().getLocation().getPath();
    String projectDirectory = testPath.substring(0, testPath.indexOf("target")) 
        + "/src/test/resources/project_dir";
    example.setMyField("abc");
    assertThat(example)
        .usingProjectDirectory(projectDirectory)
        .serializesAsExpected();    
  }
  
  @Test
  public void should_pass_on_schema_match() {         
    Example example = new Example();
    example.setMyField("abc");
    assertThat(example)
        .schemaAsExpected();     
  }
}

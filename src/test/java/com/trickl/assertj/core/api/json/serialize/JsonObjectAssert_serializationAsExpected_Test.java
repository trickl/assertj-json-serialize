package com.trickl.assertj.core.api.json.serialize;

import static com.trickl.assertj.core.api.JsonObjectAssertions.assertThat;

import com.trickl.assertj.core.api.json.JsonAssert;
import com.trickl.assertj.examples.ExampleEnum;
import com.trickl.assertj.examples.ExampleObject;
import com.trickl.assertj.examples.NestedComplexExample;
import com.trickl.assertj.examples.NestedExample;

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
    ExampleObject example = new ExampleObject();
    assertThat(example)
        .deserializesWithoutError();     
  }
  
  @Test
  public void should_pass_on_deserialization_equality() {         
    ExampleObject example = new ExampleObject();
    example.setMyField("abc");
    assertThat(example)
        .deserializesAsExpected();     
  }
      
  @Test
  public void should_pass_on_serialization_match() {         
    ExampleObject example = new ExampleObject();
    example.setMyField("abc");
    assertThat(example)
        .serializesAsExpected();     
  }

  @Test
  public void should_allow_explicit_project_directory() {    
    ExampleObject example = new ExampleObject();
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
    ExampleObject example = new ExampleObject();
    example.setMyField("abc");
    assertThat(example)
        .schemaAsExpected();     
  }

  @Test
  public void should_respect_no_inlining_simple() {         
    NestedExample nested = new NestedExample();        
    assertThat(nested)
        .excludeInlineSchemaPackage("com.trickl.assertj.examples")
        .schemaAsExpected();     
  }

  @Test
  public void should_respect_allow_inlining() {         
    NestedExample nested = new NestedExample();        
    assertThat(nested)
        .withSchemaFileExtension(".schema2.json")               
        .schemaAsExpected();     
  }

  @Test
  public void can_disallow_additional_properties() {         
    ExampleObject example = new ExampleObject();        
    assertThat(example)
        .disallowAdditionalProperties()
        .withSchemaFileExtension(".schema2.json")               
        .schemaAsExpected();     
  }

  @Test
  public void should_respect_no_inlining_complex() {         
    NestedComplexExample nested = new NestedComplexExample();        
    assertThat(nested)           
        .excludeInlineSchemaPackage("com.trickl.assertj.examples")
        .schemaAsExpected();     
  }

  @Test
  public void should_handle_enums() {         
    ExampleEnum example = ExampleEnum.SECOND;
    assertThat(example)
        .serializesAsExpected()
        .schemaAsExpected();     
  }
}

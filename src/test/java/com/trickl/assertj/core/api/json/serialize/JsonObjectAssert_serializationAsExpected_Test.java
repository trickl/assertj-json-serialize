package com.trickl.assertj.core.api.json.serialize;

import com.trickl.assertj.core.api.json.JsonAssert;
import static com.trickl.assertj.core.api.JsonObjectAssertions.assertThat;
import org.junit.Test;

/**
 * Tests for <code>{@link JsonAssert#isSameJsonAs(java.io.File)}</code>.
 * 
 * @author Yvonne Wang
 */
public class JsonObjectAssert_serializationAsExpected_Test extends JsonObjectAssertBaseTest {

  @Override
  protected JsonObjectAssert invoke_api_method() {
    return assertions.serializationAsExpected();
  }

  @Override
  protected void verify_internal_effects() {
  }
      
  @Test
  public void should_pass() {    
     assertThat("abc")
        .serializationAsExpected();     
  }
}

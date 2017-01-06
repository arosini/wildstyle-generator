package ar.wildstyle;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;
import ar.wildstyle.test.ExamplePojo;
import ar.wildstyle.valuegenerator.IntegerValueGenerator;
import ar.wildstyle.valuegenerator.StringValueGenerator;
import ar.wildstyle.valuegenerator.ValueGenerator;

/**
 * {@code FieldMappingEntryTests} contains tests for the {@link FieldMappingEntry} class.
 *
 * @author Adam Rosini
 */
public class FieldMappingEntryTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() throws Exception {
      this.field = ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME);
      this.valueGenerator = new StringValueGenerator();
      this.value = "stringValue";
   }

   /**
    * Test for creating a valid {@link FieldMappingEntry} using a {@link ValueGenerator}.
    */
   @Test
   public void fieldMappingEntryValueGenerator() {
      final FieldMappingEntry<String> fieldMappingEntry = new FieldMappingEntry<String>(this.field, this.valueGenerator);

      Assert.assertEquals(this.field, fieldMappingEntry.getField());
      Assert.assertNotNull(fieldMappingEntry.getOrGenerateValue());
   }

   /**
    * Test for creating a valid {@link FieldMappingEntry} using a value.
    */
   @Test
   public void fieldMappingEntryValue() {
      final FieldMappingEntry<String> fieldMappingEntry = new FieldMappingEntry<String>(this.field, this.value);

      Assert.assertEquals(this.field, fieldMappingEntry.getField());
      Assert.assertEquals(fieldMappingEntry.getOrGenerateValue(), this.value);
   }


   /**
    * Test for creating a valid {@link FieldMappingEntry} using a {@code null} value.
    */
   @Test
   public void fieldMappingEntryNullValue() {
      this.value = null;

      final FieldMappingEntry<String> fieldMappingEntry = new FieldMappingEntry<String>(this.field, this.value);

      Assert.assertEquals(this.field, fieldMappingEntry.getField());
      Assert.assertNull(fieldMappingEntry.getOrGenerateValue());
   }

   /**
    * Test for attempting to create a {@link FieldMappingEntry} (with a value generator) using a null field parameter.
    */
   @Test
   public void fieldMappingEntryValueGeneratorNullField() {
      this.expectedException.expect(AssertionError.class);
      this.field = null;

      new FieldMappingEntry<String>(this.field, this.valueGenerator);
   }

   /**
    * Test for attempting to create a {@link FieldMappingEntry} (with a value) using a null field parameter.
    */
   @Test
   public void fieldMappingEntryValueNullField() {
      this.expectedException.expect(AssertionError.class);
      this.field = null;

      new FieldMappingEntry<String>(this.field, this.value);
   }

   /**
    * Test for attempting to create a {@link FieldMappingEntry} using a null value generator parameter.
    */
   @Test
   public void fieldMappingEntryNullValueGenerator() {
      this.expectedException.expect(AssertionError.class);
      this.valueGenerator = null;

      new FieldMappingEntry<String>(this.field, this.valueGenerator);
   }

   /**
    * Test for attempting to create a {@link FieldMappingEntry} where the value generator parameter is not compatible with the field
    * parameter.
    */
   @Test
   public void fieldMappingEntryIncompatibleFieldAndValueGenerator() {
      this.expectedException.expect(AssertionError.class);

      new FieldMappingEntry<Integer>(this.field, new IntegerValueGenerator());
   }

   /**
    * Test for attempting to create a {@link FieldMappingEntry} where the value parameter is not compatible with the field parameter.
    */
   @Test
   public void fieldMappingEntryIncompatibleFieldAndValue() {
      this.expectedException.expect(AssertionError.class);

      new FieldMappingEntry<Integer>(this.field, 1);
   }

   /**
    * An example field to use when creating a {@link FieldMappingEntry}.
    */
   private Field field;

   /**
    * An example value generator to use when creating a {@link FieldMappingEntry}.
    */
   private ValueGenerator<String> valueGenerator;

   /**
    * An example value to use when creating a {@link FieldMappingEntry}.
    */
   private String value;

}

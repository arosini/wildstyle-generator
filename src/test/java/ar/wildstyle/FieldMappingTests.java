package ar.wildstyle;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;
import ar.wildstyle.test.ExamplePojo;
import ar.wildstyle.test.ExamplePojoParent;
import ar.wildstyle.valuegenerator.IntegerValueGenerator;
import ar.wildstyle.valuegenerator.StringValueGenerator;
import ar.wildstyle.valuegenerator.ValueGenerator;

/**
 * {@code FieldMappingTests} contains tests for the {@link FieldMapping} class.
 *
 * @author Adam Rosini
 */
public class FieldMappingTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() {
      this.fieldMapping = new FieldMapping<>(ExamplePojo.class);
      this.stringFieldName = ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME;
      this.stringValue = "stringValue";
      this.stringValueGenerator = new StringValueGenerator();
      this.length1StringValueGenerator = new StringValueGenerator(1, 1);
      this.length2StringValueGenerator = new StringValueGenerator(2, 2);
   }

   /**
    * Test for a valid invocation of {@link FieldMapping#merge}.
    */
   @Test
   public void merge() throws Exception {
      final String childValue = "child";
      final String parentValue = "parent";

      final FieldMapping<ExamplePojo> fieldMappingA = new FieldMapping<>(ExamplePojo.class);
      fieldMappingA.map(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, childValue);
      fieldMappingA.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, childValue);

      final FieldMapping<ExamplePojo> fieldMappingB = new FieldMapping<>(ExamplePojo.class);
      fieldMappingB.map(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, parentValue);
      fieldMappingB.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, parentValue);
      fieldMappingB.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, parentValue);

      final FieldMapping<ExamplePojo> mergedFieldMapping = FieldMapping.merge(fieldMappingA, fieldMappingB);

      final Field examplePrivateStringField = ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME);
      final Field exampleParentPrivateStringField =
         ExamplePojoParent.class.getDeclaredField(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME);
      final Field exampleDuplicatePrivateStringFieldChild =
         ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME);
      final Field exampleDuplicatePrivateStringFieldParent =
         ExamplePojoParent.class.getDeclaredField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME);

      final Map<Field, Object> mergedFieldMappingAsMap = new HashMap<>();
      for (final FieldMappingEntry<?> fieldMappingEntry : mergedFieldMapping) {
         mergedFieldMappingAsMap.put(fieldMappingEntry.getField(), fieldMappingEntry.getOrGenerateValue());
      }

      Assert.assertEquals(4, mergedFieldMappingAsMap.size());
      Assert.assertEquals(childValue, mergedFieldMappingAsMap.get(examplePrivateStringField));
      Assert.assertEquals(parentValue, mergedFieldMappingAsMap.get(exampleParentPrivateStringField));
      Assert.assertEquals(childValue, mergedFieldMappingAsMap.get(exampleDuplicatePrivateStringFieldChild));
      Assert.assertEquals(parentValue, mergedFieldMappingAsMap.get(exampleDuplicatePrivateStringFieldParent));
   }

   /**
    * Test for a valid invocation of {@link FieldMapping#merge} where field mapping B's type is assignable from field mapping A's type.
    */
   @Test
   public void mergeWithParent() throws Exception {
      final String childValue = "child";
      final String parentValue = "parent";

      final FieldMapping<ExamplePojo> fieldMappingA = new FieldMapping<>(ExamplePojo.class);
      fieldMappingA.map(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, childValue);
      fieldMappingA.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, childValue);
      fieldMappingA.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, childValue);

      final FieldMapping<ExamplePojoParent> fieldMappingB = new FieldMapping<>(ExamplePojoParent.class);
      fieldMappingB.map(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, parentValue);
      fieldMappingB.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, parentValue);

      final FieldMapping<ExamplePojo> mergedFieldMapping = FieldMapping.merge(fieldMappingA, fieldMappingB);

      final Field examplePrivateStringField = ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME);
      final Field exampleParentPrivateStringField =
         ExamplePojoParent.class.getDeclaredField(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME);
      final Field exampleDuplicatePrivateStringFieldChild =
         ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME);
      final Field exampleDuplicatePrivateStringFieldParent =
         ExamplePojoParent.class.getDeclaredField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME);

      final Map<Field, Object> mergedFieldMappingAsMap = new HashMap<>();
      for (final FieldMappingEntry<?> fieldMappingEntry : mergedFieldMapping) {
         mergedFieldMappingAsMap.put(fieldMappingEntry.getField(), fieldMappingEntry.getOrGenerateValue());
      }

      Assert.assertEquals(4, mergedFieldMappingAsMap.size());
      Assert.assertEquals(childValue, mergedFieldMappingAsMap.get(examplePrivateStringField));
      Assert.assertEquals(parentValue, mergedFieldMappingAsMap.get(exampleParentPrivateStringField));
      Assert.assertEquals(childValue, mergedFieldMappingAsMap.get(exampleDuplicatePrivateStringFieldChild));
      Assert.assertEquals(childValue, mergedFieldMappingAsMap.get(exampleDuplicatePrivateStringFieldParent));
   }

   /**
    * Test for creating a valid {@link FieldMapping}.
    */
   @Test
   public void fieldMapping() {
      Assert.assertEquals(ExamplePojo.class, this.fieldMapping.getType());
   }

   /**
    * Test for creating a valid anonymous {@link FieldMapping}.
    */
   @Test
   public void fieldMappingAnonymous() {
      this.fieldMapping = new FieldMapping<ExamplePojo>(ExamplePojo.class) {
         {
         }
      };

      Assert.assertEquals(ExamplePojo.class, this.fieldMapping.getType());
   }

   /**
    * Test for attempting to create a {@link FieldMapping} with a null type parameter.
    */
   @Test
   public void fieldMappingNullType() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'type' parameter cannot be null.");

      this.fieldMapping = new FieldMapping<>(null);
   }

   /**
    * Test for a valid invocation of {@link FieldMapping#map} with a private field's name and a value generator.
    */
   @Test
   public void mapPrivateFieldToValueGenerator() {
      this.fieldMapping.map(this.stringFieldName, this.length1StringValueGenerator);

      final FieldMappingEntry<?> fieldMappingEntry = this.fieldMapping.iterator().next();
      Assert.assertEquals(this.stringFieldName, fieldMappingEntry.getField().getName());
      Assert.assertEquals(1, fieldMappingEntry.getOrGenerateValue().toString().length());
   }

   /**
    * Test for a valid invocation of {@link FieldMapping#map} with a private field's name and a value.
    */
   @Test
   public void mapPrivateFieldToValue() {
      this.fieldMapping.map(this.stringFieldName, this.stringValue);

      final FieldMappingEntry<?> fieldMappingEntry = this.fieldMapping.iterator().next();
      Assert.assertEquals(this.stringFieldName, fieldMappingEntry.getField().getName());
      Assert.assertEquals(this.stringValue, fieldMappingEntry.getOrGenerateValue());
   }

   /**
    * Test for a valid invocation of {@link FieldMapping#map} with a private field's name and a null value.
    */
   @Test
   public void mapPrivateFieldToNullValue() {
      this.stringValue = null;
      this.fieldMapping.map(this.stringFieldName, this.stringValue);

      final FieldMappingEntry<?> fieldMappingEntry = this.fieldMapping.iterator().next();
      Assert.assertEquals(this.stringFieldName, fieldMappingEntry.getField().getName());
      Assert.assertNull(fieldMappingEntry.getOrGenerateValue());
   }

   /**
    * Test for a valid invocation of {@link FieldMapping#map} with a parent's private field name and a value generator.
    */
   @Test
   public void mapParentPrivateFieldToValueGenerator() {
      this.stringFieldName = ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME;

      this.fieldMapping.map(this.stringFieldName, this.length1StringValueGenerator);

      final FieldMappingEntry<?> fieldMappingEntry = this.fieldMapping.iterator().next();
      Assert.assertEquals(this.stringFieldName, fieldMappingEntry.getField().getName());
      Assert.assertEquals(1, fieldMappingEntry.getOrGenerateValue().toString().length());
   }

   /**
    * Test for a valid invocation of {@link FieldMapping#map} with a parent's private field name and value.
    */
   @Test
   public void mapParentPrivateFieldToValue() {
      this.stringFieldName = ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME;

      this.fieldMapping.map(this.stringFieldName, this.stringValue);

      final FieldMappingEntry<?> fieldMappingEntry = this.fieldMapping.iterator().next();
      Assert.assertEquals(this.stringFieldName, fieldMappingEntry.getField().getName());
      Assert.assertEquals(this.stringValue, fieldMappingEntry.getOrGenerateValue());
   }

   /**
    * Test for multiple valid invocations of {@link FieldMapping#map} for equivalent fields (same name and type) in a class's hierarchy.
    */
   @Test
   public void mapEquivalentFields() throws Exception {
      this.stringFieldName = ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME;
      final Field childField = ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME);
      final Field parentField = ExamplePojoParent.class.getDeclaredField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME);

      // Add the first field mapping and assert it is mapped to the child field.
      this.fieldMapping.map(this.stringFieldName, this.length1StringValueGenerator);

      FieldMappingEntry<?> childFieldMappingEntry = this.fieldMapping.iterator().next();
      Assert.assertEquals(childField, childFieldMappingEntry.getField());
      Assert.assertEquals(1, childFieldMappingEntry.getOrGenerateValue().toString().length());

      // Add the second field mapping and assert the first field mapping is mapped to the child
      // field and the second is mapped to the parent field.
      this.fieldMapping.map(this.stringFieldName, this.length2StringValueGenerator);

      final Iterator<FieldMappingEntry<?>> iterator = this.fieldMapping.iterator();

      final FieldMappingEntry<?> parentFieldMappingEntry = iterator.next();
      Assert.assertEquals(parentField, parentFieldMappingEntry.getField());
      Assert.assertEquals(2, parentFieldMappingEntry.getOrGenerateValue().toString().length());

      childFieldMappingEntry = iterator.next();
      Assert.assertEquals(childField, childFieldMappingEntry.getField());
      Assert.assertEquals(1, childFieldMappingEntry.getOrGenerateValue().toString().length());
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a private field's name and a null value generator.
    */
   @Test
   public void mapPrivateFieldToNullValueGenerator() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'valueGenerator' parameter cannot be null.");

      this.fieldMapping.map(this.stringFieldName, null);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a null field name and a value generator.
    */
   @Test
   public void mapNullFieldNameToValueGenerator() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'fieldName' parameter cannot be null.");

      this.fieldMapping.map(null, this.stringValueGenerator);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a null field name and a value.
    */
   @Test
   public void mapNullFieldNameToValue() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'fieldName' parameter cannot be null.");

      this.fieldMapping.map(null, this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with an empty field name and a value generator.
    */
   @Test
   public void mapEmptyFieldNameToValueGenerator() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'fieldName' parameter cannot be empty.");

      this.fieldMapping.map("", this.stringValueGenerator);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with an empty field name and a value.
    */
   @Test
   public void mapEmptyFieldNameToValue() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'fieldName' parameter cannot be empty.");

      this.fieldMapping.map("", this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a field name that is not associated with a field and a value generator.
    */
   @Test
   public void mapNonExistentFieldToValueGenerator() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "Could not find a field named 'nonExistentField' that is assignable from 'java.lang.String' " +
            "for the 'ar.wildstyle.test.ExamplePojo' class or any of its parents.");

      this.fieldMapping.map("nonExistentField", this.stringValueGenerator);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a field name that is not associated with a field and a value.
    */
   @Test
   public void mapNonExistentFieldToValue() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "Could not find a field named 'nonExistentField' that is assignable from 'java.lang.String' " +
            "for the 'ar.wildstyle.test.ExamplePojo' class or any of its parents.");

      this.fieldMapping.map("nonExistentField", this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a private field's name and a value generator that generates values which
    * are incompatible with the field associated with the given field name.
    */
   @Test
   public void mapPrivateFieldToIncompatibleValueGenerator() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "Could not find a field named 'examplePrivateString' that is assignable from 'java.lang.Integer' " +
            "for the 'ar.wildstyle.test.ExamplePojo' class or any of its parents.");

      this.fieldMapping.map(this.stringFieldName, new IntegerValueGenerator());
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a private field's name and a value that is incompatible with the field
    * associated with the given field name.
    */
   @Test
   public void mapPrivateFieldToIncompatibleValue() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "Could not find a field named 'examplePrivateString' that is assignable from 'java.lang.Integer' " +
            "for the 'ar.wildstyle.test.ExamplePojo' class or any of its parents.");

      this.fieldMapping.map(this.stringFieldName, 1);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a field name that is associated with a field that is already mapped to a
    * value generator and a value generator.
    */
   @Test
   public void mapPrivateFieldDuplicateWithValueGenerators() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "The 'examplePrivateString' field that is assignable from 'java.lang.String' has already been mapped " +
            "as many times as possible in this field mapping.");

      this.fieldMapping.map(this.stringFieldName, this.stringValueGenerator);
      this.fieldMapping.map(this.stringFieldName, this.stringValueGenerator);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a field name that is associated with a field that is already mapped to a
    * value and a value.
    */
   @Test
   public void mapPrivateFieldDuplicateWithValues() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "The 'examplePrivateString' field that is assignable from 'java.lang.String' has already been mapped " +
            "as many times as possible in this field mapping.");

      this.fieldMapping.map(this.stringFieldName, this.stringValue);
      this.fieldMapping.map(this.stringFieldName, this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a field name that is associated with a field that is already mapped to a
    * value generator and a value.
    */
   @Test
   public void mapPrivateFieldDuplicateWithValueGeneratorThenValue() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "The 'examplePrivateString' field that is assignable from 'java.lang.String' has already been mapped " +
            "as many times as possible in this field mapping.");

      this.fieldMapping.map(this.stringFieldName, this.stringValueGenerator);
      this.fieldMapping.map(this.stringFieldName, this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} with a field name that is associated with a field that is already mapped to a
    * value and a value generator.
    */
   @Test
   public void mapPrivateFieldDuplicateWithValueThenValueGenerator() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "The 'examplePrivateString' field that is assignable from 'java.lang.String' has already been mapped " +
            "as many times as possible in this field mapping.");

      this.fieldMapping.map(this.stringFieldName, this.stringValue);
      this.fieldMapping.map(this.stringFieldName, this.stringValueGenerator);
   }

   /**
    * Test for attempting to invoke {@link FieldMapping#map} too many times for equivalent fields (same name and type) in a class's
    * hierarchy.
    */
   @Test
   public void addTooManyFieldMappingEntriesForEquivalentFields() {
      this.stringFieldName = ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME;

      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "The '" + ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME + "' field that is assignable from " +
            "'java.lang.String' has already been mapped as many times as possible in this field mapping.");

      this.fieldMapping.map(this.stringFieldName, this.stringValueGenerator);
      this.fieldMapping.map(this.stringFieldName, this.stringValueGenerator);
      this.fieldMapping.map(this.stringFieldName, this.stringValueGenerator);
   }


   /**
    * An example field mapping that is recreated before each test.
    */
   private FieldMapping<ExamplePojo> fieldMapping;

   /**
    * An example {@code String} {@linkplain Field field} name.
    */
   private String stringFieldName;

   /**
    * An example {@code String} value.
    */
   private String stringValue;

   /**
    * An example {@code String} value generator.
    */
   private ValueGenerator<String> stringValueGenerator;

   /**
    * An example value generator that generates String's of length 1.
    */
   private ValueGenerator<String> length1StringValueGenerator;

   /**
    * An example value generator that generates String's of length 2.
    */
   private ValueGenerator<String> length2StringValueGenerator;

}

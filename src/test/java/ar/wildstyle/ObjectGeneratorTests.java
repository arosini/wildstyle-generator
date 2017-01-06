package ar.wildstyle;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;
import ar.wildstyle.test.ExamplePojo;
import ar.wildstyle.test.ExamplePojoParent;
import ar.wildstyle.valuegenerator.IntegerValueGenerator;
import ar.wildstyle.valuegenerator.StringValueGenerator;

/**
 * {@code ObjectGeneratorTests} contains tests for the {@link ObjectGeneratorTest} class.
 *
 * @author Adam Rosini
 */
public class ObjectGeneratorTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() {
      this.childStringFieldValue = "childStringFieldValue";
      this.parentStringFieldValue = "parentStringFieldValue";

      this.objectGeneratorParent = new ObjectGenerator<ExamplePojo>(
         "parent",
         new FieldMapping<ExamplePojo>(ExamplePojo.class) {
            {
               this.map(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
               this.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
               this.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
            }
         },
         Arrays.asList(new StringValueGenerator()),
         0,
         null);
   }

   /**
    * Test for creating a valid object generator.
    */
   @Test
   public void objectGenerator() {
      final ObjectGenerator<ExamplePojo> objectGenerator = new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class) {
            {
               this.map(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
            }
         },
         Arrays.asList(this.childStringFieldValue),
         0,
         null);

      Assert.assertEquals(ExamplePojo.class, objectGenerator.getValueType());
      Assert.assertEquals(ObjectGenerator.DEFAULT_NAME, objectGenerator.getName());
      Assert.assertEquals(false, objectGenerator.canGenerateNull());

      final ExamplePojo examplePojo = objectGenerator.generateValue();
      Assert.assertEquals(this.childStringFieldValue, examplePojo.getExamplePrivateString());
      Assert.assertEquals(this.parentStringFieldValue, examplePojo.getExampleParentPrivateString());
   }

   /**
    * Test for creating a valid object generator with value generator constructor arguments.
    */
   @Test
   public void objectGeneratorValueGeneratorConstructorArguments() {
      final ObjectGenerator<ExamplePojo> objectGenerator = new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class) {
            {
               this.map(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
            }
         },
         Arrays.asList(new StringValueGenerator(1, 1)),
         0,
         null);

      Assert.assertEquals(ExamplePojo.class, objectGenerator.getValueType());
      Assert.assertEquals(ObjectGenerator.DEFAULT_NAME, objectGenerator.getName());
      Assert.assertEquals(false, objectGenerator.canGenerateNull());

      final ExamplePojo examplePojo = objectGenerator.generateValue();
      Assert.assertEquals(1, examplePojo.getExamplePrivateString().length());
      Assert.assertEquals(this.parentStringFieldValue, examplePojo.getExampleParentPrivateString());
   }

   /**
    * Test for creating a valid object generator that uses a default constructor.
    */
   @Test
   public void objectGeneratorDefaultConstructor() {
      final ObjectGenerator<ExamplePojo> objectGenerator = new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class) {
            {
               this.map(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
            }
         },
         Arrays.asList(),
         0,
         null);

      Assert.assertEquals(ExamplePojo.class, objectGenerator.getValueType());
      Assert.assertEquals(ObjectGenerator.DEFAULT_NAME, objectGenerator.getName());
      Assert.assertEquals(false, objectGenerator.canGenerateNull());

      final ExamplePojo examplePojo = objectGenerator.generateValue();
      Assert.assertNull(examplePojo.getExamplePrivateString());
      Assert.assertEquals(this.parentStringFieldValue, examplePojo.getExampleParentPrivateString());
   }

   /**
    * Test for creating a valid object generator that never generates a null value.
    */
   @Test
   public void objectGeneratorNeverNullValue() {
      final ObjectGenerator<ExamplePojo> objectGenerator = new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         Arrays.asList(),
         0,
         null);

      Assert.assertFalse(objectGenerator.canGenerateNull());
      for (int x = 0; x < 1000; x++) {
         Assert.assertNotNull(objectGenerator.generateValue());
      }
   }

   /**
    * Test for creating a valid object generator that always generates a null value.
    */
   @Test
   public void objectGeneratorAlwaysNullValue() {
      final ObjectGenerator<ExamplePojo> objectGenerator = new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         Arrays.asList(),
         100,
         null);

      for (int x = 0; x < 1000; x++) {
         Assert.assertNull(objectGenerator.generateValue());
      }
   }

   /**
    * Test for creating a valid object generator which inherits from another object generator.
    */
   @Test
   public void objectGeneratorWithParent() {
      final ObjectGenerator<ExamplePojo> objectGenerator = new ObjectGenerator<ExamplePojo>(
         "child",
         new FieldMapping<ExamplePojo>(ExamplePojo.class) {
            {
               this.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.childStringFieldValue);
            }
         },
         Arrays.asList(this.childStringFieldValue),
         0,
         this.objectGeneratorParent);

      final ExamplePojo examplePojo = objectGenerator.generateValue();

      Assert.assertEquals(this.childStringFieldValue, examplePojo.getExamplePrivateString());
      Assert.assertEquals(this.parentStringFieldValue, examplePojo.getExampleParentPrivateString());
      Assert.assertEquals(this.childStringFieldValue, examplePojo.getExampleDuplicatePrivateStringChild());
      Assert.assertEquals(this.parentStringFieldValue, examplePojo.getExampleDuplicatePrivateStringParent());
      Assert.assertEquals(0, examplePojo.getExamplePrivateInt());
      Assert.assertNull(examplePojo.getExamplePrivateInteger());
   }

   /**
    * Test for creating a valid object generator which inherits from another object generator of a super class.
    */
   @Test
   public void objectGeneratorWithSuperParent() {
      final ObjectGenerator<ExamplePojoParent> objectGeneratorParent = new ObjectGenerator<ExamplePojoParent>(
         "parent",
         new FieldMapping<ExamplePojoParent>(ExamplePojoParent.class) {
            {
               this.map(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
               this.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.parentStringFieldValue);
            }
         },
         Arrays.asList(),
         0,
         null);

      Assert.assertNotNull(objectGeneratorParent);

      final ObjectGenerator<ExamplePojo> objectGenerator = new ObjectGenerator<ExamplePojo>(
         "child",
         new FieldMapping<ExamplePojo>(ExamplePojo.class) {
            {
               this.map(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.childStringFieldValue);
               this.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.childStringFieldValue);
               this.map(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, ObjectGeneratorTests.this.childStringFieldValue);
            }
         },
         Arrays.asList(this.childStringFieldValue),
         0,
         objectGeneratorParent);

      final ExamplePojo examplePojo = objectGenerator.generateValue();

      Assert.assertEquals(this.childStringFieldValue, examplePojo.getExamplePrivateString());
      Assert.assertEquals(this.parentStringFieldValue, examplePojo.getExampleParentPrivateString());
      Assert.assertEquals(this.childStringFieldValue, examplePojo.getExampleDuplicatePrivateStringChild());
      Assert.assertEquals(this.childStringFieldValue, examplePojo.getExampleDuplicatePrivateStringParent());
      Assert.assertEquals(0, examplePojo.getExamplePrivateInt());
      Assert.assertNull(examplePojo.getExamplePrivateInteger());
   }

   /**
    * Test for attempting to create an object generator with a constructor arguments parameter that does not correspond to a constructor for
    * the value type parameter.
    */
   @Test
   public void objectGeneratorInvalidConstructorArguments() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("java.lang.IllegalArgumentException: Could not construct an instance of " +
         "'class ar.wildstyle.test.ExamplePojo' using the resolved list of constructor arguments: [1]");

      new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         Arrays.asList(1),
         0,
         null);
   }

   /**
    * Test for attempting to create an object generator with a null name parameter.
    */
   @Test
   public void objectGeneratorNullName() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'name' parameter cannot be null.");

      new ObjectGenerator<ExamplePojo>(
         null,
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         Arrays.asList(),
         0,
         null);
   }

   /**
    * Test for attempting to create an object generator with an empty name parameter.
    */
   @Test
   public void objectGeneratorEmptyName() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'name' parameter cannot be empty.");

      new ObjectGenerator<ExamplePojo>(
         "",
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         Arrays.asList(),
         0,
         null);
   }

   /**
    * Test for attempting to create an object generator with a null field mapping parameter.
    */
   @Test
   public void objectGeneratorNullFieldMapping() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'fieldMapping' parameter cannot be null.");

      new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         null,
         Arrays.asList(),
         0,
         null);
   }

   /**
    * Test for attempting to create an object generator with a null constructor arguments parameter.
    */
   @Test
   public void objectGeneratorNullConstructorArgs() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'constructorArgs' parameter cannot be null.");

      new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         null,
         0,
         null);
   }

   /**
    * Test for attempting to create an object generator with a null chance parameter that is less than 0.
    */
   @Test
   public void objectGeneratorNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         Arrays.asList(new IntegerValueGenerator()),
         -0.1,
         null);
   }

   /**
    * Test for attempting to create an object generator with a null chance parameter that is greater than 100.
    */
   @Test
   public void objectGeneratorNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new ObjectGenerator<ExamplePojo>(
         ObjectGenerator.DEFAULT_NAME,
         new FieldMapping<ExamplePojo>(ExamplePojo.class),
         Arrays.asList(),
         100.1,
         null);
   }

   /**
    * An example value for a {@code String} {@linkplain Field field} that is not equal to {@link #parentStringFieldValue}.
    */
   private String childStringFieldValue;

   /**
    * An example value for a {@code String} {@linkplain Field field} that is not equal to {@link #childStringFieldValue}.
    */
   private String parentStringFieldValue;

   /**
    * An example of an object generator's parent.
    */
   private ObjectGenerator<ExamplePojo> objectGeneratorParent;

}

package ar.wildstyle;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;
import ar.wildstyle.test.ConstructorArgPojo;
import ar.wildstyle.test.ExamplePojo;
import ar.wildstyle.test.ExamplePojo.ExamplePojoEnum;
import ar.wildstyle.test.ExamplePojoParent;
import ar.wildstyle.valuegenerator.BooleanValueGenerator;
import ar.wildstyle.valuegenerator.ByteValueGenerator;
import ar.wildstyle.valuegenerator.DoubleValueGenerator;
import ar.wildstyle.valuegenerator.EnumValueGenerator;
import ar.wildstyle.valuegenerator.FirstNameValueGenerator;
import ar.wildstyle.valuegenerator.FloatValueGenerator;
import ar.wildstyle.valuegenerator.IntegerValueGenerator;
import ar.wildstyle.valuegenerator.LastNameValueGenerator;
import ar.wildstyle.valuegenerator.LongValueGenerator;
import ar.wildstyle.valuegenerator.ShortValueGenerator;
import ar.wildstyle.valuegenerator.StringValueGenerator;
import ar.wildstyle.valuegenerator.ValueGenerator;

/**
 * {@code WildstyleGeneratorTests} contains tests for the {@link WildstyleGenerator} class.
 *
 * @author Adam Rosini
 */
public class WildstyleGeneratorTests extends BaseTest {

   /**
    * Test for {@linkplain ObjectGeneratorBuilder#register registering} and using an {@linkplain ObjectGenerator object generator} to
    * {@linkplain WildstyleGenerator#generate generate} an {@link ExamplePojo} instance. This test is meant to act as an integration test
    * for the entire project, utilizing as many different features as possible.
    */
   @Test
   public void createRegisterAndGenerate() {
      // Create an embedded generator.
      WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setName("embedded")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, new StringValueGenerator(1, 1))
         .setConstructorArgs(null, 1, new IntegerValueGenerator())
         .register();

      // Create a parent object generator. Handle special cases here.
      WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setName("parent")
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, new StringValueGenerator(6, 6))
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, "parentDuplicatePrivateString")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_WITHOUT_GETTER_NAME, "examplePrivateStringFieldWithoutGetter")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_FIRST_NAME_STRING_FIELD_NAME, new FirstNameValueGenerator(true))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_LAST_NAME_STRING_FIELD_NAME, new LastNameValueGenerator(true))
         .register();

      // Create an object with all fields filled out.
      WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setParent(WildstyleGenerator.getObjectGenerator(ExamplePojo.class, "parent"))
         // Private fields
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_BOOLEAN_WRAPPER_FIELD_NAME, false)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_BOOLEAN_FIELD_NAME, new BooleanValueGenerator(100))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_BYTE_WRAPPER_FIELD_NAME, new ByteValueGenerator((byte)44, (byte)44))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_BYTE_FIELD_NAME, (byte)-120)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_CHARACTER_FIELD_NAME, 'c')
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_CHAR_FIELD_NAME, 'a')
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_DOUBLE_WRAPPER_FIELD_NAME, new DoubleValueGenerator(5.5, 5.5))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_DOUBLE_FIELD_NAME, 5.4)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_ENUM_FIELD_NAME,
            new EnumValueGenerator<ExamplePojoEnum>(ExamplePojoEnum.class, false))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_FLOAT_WRAPPER_FIELD_NAME, new FloatValueGenerator(5.3f, 5.3f))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_FLOAT_FIELD_NAME, 5.2f)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_INTEGER_FIELD_NAME, 3)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_INT_FIELD_NAME, new IntegerValueGenerator(4, 4))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_LONG_WRAPPER_FIELD_NAME, new LongValueGenerator(5, 5))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_LONG_FIELD_NAME, 2L)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_OBJECT_FIELD_NAME,
            WildstyleGenerator.getObjectGenerator(ExamplePojo.class, "embedded"))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_SHORT_WRAPPER_FIELD_NAME, new ShortValueGenerator((short)19, (short)19))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_SHORT_FIELD_NAME, (short)-4)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, "privateString")

         // Parent private fields
         .mapField(ExamplePojoParent.EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME, new StringValueGenerator(5, 5))

         // Special cases
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, new StringValueGenerator(7, 7))

         .register();

      // Generate the value.
      final ExamplePojo examplePojo = WildstyleGenerator.generate(ExamplePojo.class);
      Assert.assertNotNull(examplePojo);

      // Assert private fields.
      Assert.assertFalse(examplePojo.getExamplePrivateBooleanWrapper());
      Assert.assertTrue(examplePojo.getExamplePrivateBoolean());

      Assert.assertEquals(Byte.valueOf((byte)44), examplePojo.getExamplePrivateByteWrapper());
      Assert.assertEquals((byte)-120, examplePojo.getExamplePrivateByte());

      Assert.assertEquals(Character.valueOf('c'), examplePojo.getExamplePrivateCharacter());
      Assert.assertEquals('a', examplePojo.getExamplePrivateChar());

      Assert.assertEquals(Double.valueOf(5.5), examplePojo.getExamplePrivateDoubleWrapper());
      Assert.assertEquals(5.4, examplePojo.getExamplePrivateDouble(), 0);

      Assert.assertNotNull(examplePojo.getExamplePrivateEnum());

      Assert.assertEquals(Float.valueOf(5.3f), examplePojo.getExamplePrivateFloatWrapper());
      Assert.assertEquals(5.2f, examplePojo.getExamplePrivateFloat(), 0);

      Assert.assertEquals(Integer.valueOf(3), examplePojo.getExamplePrivateInteger());
      Assert.assertEquals(4, examplePojo.getExamplePrivateInt());

      Assert.assertEquals(Long.valueOf(5), examplePojo.getExamplePrivateLongWrapper());
      Assert.assertEquals(2L, examplePojo.getExamplePrivateLong());

      Assert.assertEquals(Short.valueOf((short)19), examplePojo.getExamplePrivateShortWrapper());
      Assert.assertEquals((short)-4, examplePojo.getExamplePrivateShort());

      Assert.assertEquals("privateString", examplePojo.getExamplePrivateString());

      // Assert parent private fields.
      Assert.assertEquals(5, examplePojo.getExampleParentPrivateString().length());

      // Assert special cases.
      Assert.assertEquals(7, examplePojo.getExampleDuplicatePrivateStringChild().length());
      Assert.assertEquals("parentDuplicatePrivateString", examplePojo.getExampleDuplicatePrivateStringParent());
      Assert.assertNull(examplePojo.getExamplePrivateStringFieldAlwaysNull());
      Assert.assertNotNull(examplePojo.getExamplePrivateFirstNameString());
      Assert.assertNotNull(examplePojo.getExamplePrivateLastNameString());

      final ExamplePojo embeddedExamplePojo = (ExamplePojo)examplePojo.getExamplePrivateObject();
      Assert.assertEquals(1, embeddedExamplePojo.getExamplePrivateString().length());
      Assert.assertNull(embeddedExamplePojo.getExamplePrivateObject());
      Assert.assertEquals(Integer.valueOf(1), embeddedExamplePojo.getExamplePrivateInteger());
      Assert.assertTrue(embeddedExamplePojo.getExamplePrivateInt() >= IntegerValueGenerator.DEFAULT_MIN);
      Assert.assertTrue(embeddedExamplePojo.getExamplePrivateInt() <= IntegerValueGenerator.DEFAULT_MAX);

      try {
         Assert.assertEquals("examplePrivateStringFieldWithoutGetter",
            FieldUtils.readField(examplePojo, ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_WITHOUT_GETTER_NAME, true));
      }
      catch (final IllegalAccessException e) {
         assert false : e.getMessage();
      }
   }

   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} and {@linkplain ObjectGeneratorBuilder#register registering}
    * an {@link ObjectGenerator} without any configuration.
    */
   @Test
   public void createObjectGenerator() {
      final ObjectGenerator<ExamplePojo> objectGenerator = WildstyleGenerator
         .createObjectGenerator(ExamplePojo.class)
         .register();

      final ExamplePojo examplePojo = objectGenerator.generateValue();

      Assert.assertEquals(ObjectGenerator.DEFAULT_NAME, objectGenerator.getName());
      Assert.assertNotNull(examplePojo);
      Assert.assertNull(examplePojo.getExamplePrivateFirstNameString());

   }

   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} an {@link ObjectGenerator} with a field mapped to a value and
    * another field mapped to a {@linkplain ValueGenerator value generator}.
    */
   @Test
   public void createObjectGeneratorFieldMapping() {
      final int firstNameLength = 1;
      final String lastName = "Smith";

      final ObjectGenerator<ExamplePojo> objectGenerator = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_FIRST_NAME_STRING_FIELD_NAME, new StringValueGenerator(firstNameLength, firstNameLength))
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_LAST_NAME_STRING_FIELD_NAME, lastName)
         .register();

      final ExamplePojo examplePojo = objectGenerator.generateValue();

      Assert.assertEquals(firstNameLength, examplePojo.getExamplePrivateFirstNameString().length());
      Assert.assertEquals(lastName, examplePojo.getExamplePrivateLastNameString());
   }

   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} an {@link ObjectGenerator} with a name.
    */
   @Test
   public void createObjectGeneratorName() {
      final String name = "testName";
      final ObjectGenerator<ExamplePojo> objectGenerator = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setName(name)
         .register();

      Assert.assertEquals(name, objectGenerator.getName());
   }

   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} an {@link ObjectGenerator} with a null chance.
    */
   @Test
   public void createObjectGeneratorNullChance() {
      final ObjectGenerator<ExamplePojo> objectGenerator = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setNullChance(100)
         .register();

      Assert.assertNull(objectGenerator.generateValue());
   }

   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} an {@link ObjectGenerator} with a list of constructor
    * arguments.
    */
   @Test
   public void createObjectGeneratorConstructorArguments() {
      final ObjectGenerator<ExamplePojo> objectGenerator = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setConstructorArgs(null, 1, new IntegerValueGenerator())
         .register();

      final ExamplePojo examplePojo = objectGenerator.generateValue();

      Assert.assertNull(examplePojo.getExamplePrivateObject());
      Assert.assertEquals(Integer.valueOf(1), examplePojo.getExamplePrivateInteger());
      Assert.assertTrue(examplePojo.getExamplePrivateInt() >= IntegerValueGenerator.DEFAULT_MIN);
      Assert.assertTrue(examplePojo.getExamplePrivateInt() <= IntegerValueGenerator.DEFAULT_MAX);
   }

   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} an {@link ObjectGenerator} with an invalid list of constructor
    * arguments.
    */
   @Test
   public void createObjectGeneratorInvalidConstructorArguments() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException
         .expectMessage("java.lang.IllegalArgumentException: Could not construct an instance of " +
            "'class ar.wildstyle.test.ExamplePojo' using the resolved list of constructor arguments: [null, null, 1, 2]");

      WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setConstructorArgs(null, null, 1, 2)
         .register();
   }

   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} an {@link ObjectGenerator} with a missing list of constructor
    * arguments.
    */
   @Test
   public void createObjectGeneratorMissingConstructorArguments() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException
         .expectMessage("java.lang.IllegalArgumentException: Could not construct an instance of " +
            "'class ar.wildstyle.test.ConstructorArgPojo' using the resolved list of constructor arguments: []");

      WildstyleGenerator.createObjectGenerator(ConstructorArgPojo.class)
         .register();
   }


   /**
    * Test for {@linkplain WildstyleGenerator#createObjectGenerator creating} an {@link ObjectGenerator} with a parent object generator.
    */
   @Test
   public void createObjectGeneratorParent() {
      final ObjectGenerator<ExamplePojo> parentObjectGenerator = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, "childDuplicatePrivateStringParent")
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, "parentDuplicatePrivateStringParent")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, "examplePrivateStringFieldParent")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_INT_FIELD_NAME, 1)
         .register();

      final ObjectGenerator<ExamplePojo> objectGenerator1 = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setName("child")
         .setParent(parentObjectGenerator)
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, "childDuplicatePrivateStringChild")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, "examplePrivateStringFieldChild")
         .register();

      final ExamplePojo examplePojo1 = objectGenerator1.generateValue();
      Assert.assertEquals("childDuplicatePrivateStringChild", examplePojo1.getExampleDuplicatePrivateStringChild());
      Assert.assertEquals("parentDuplicatePrivateStringParent", examplePojo1.getExampleDuplicatePrivateStringParent());
      Assert.assertEquals("examplePrivateStringFieldChild", examplePojo1.getExamplePrivateString());
      Assert.assertEquals(1, examplePojo1.getExamplePrivateInt());

      final ObjectGenerator<ExamplePojo> objectGenerator2 = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setName("child")
         .setParent(ExamplePojo.class)
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, "childDuplicatePrivateStringChild")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, "examplePrivateStringFieldChild")
         .register();

      final ExamplePojo examplePojo2 = objectGenerator2.generateValue();
      Assert.assertEquals("childDuplicatePrivateStringChild", examplePojo2.getExampleDuplicatePrivateStringChild());
      Assert.assertEquals("parentDuplicatePrivateStringParent", examplePojo2.getExampleDuplicatePrivateStringParent());
      Assert.assertEquals("examplePrivateStringFieldChild", examplePojo2.getExamplePrivateString());
      Assert.assertEquals(1, examplePojo2.getExamplePrivateInt());

      final ObjectGenerator<ExamplePojo> objectGenerator3 = WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setName("child")
         .setParent(ExamplePojo.class, ObjectGenerator.DEFAULT_NAME)
         .mapField(ExamplePojo.EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME, "childDuplicatePrivateStringChild")
         .mapField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME, "examplePrivateStringFieldChild")
         .register();

      final ExamplePojo examplePojo3 = objectGenerator3.generateValue();
      Assert.assertEquals("childDuplicatePrivateStringChild", examplePojo3.getExampleDuplicatePrivateStringChild());
      Assert.assertEquals("parentDuplicatePrivateStringParent", examplePojo3.getExampleDuplicatePrivateStringParent());
      Assert.assertEquals("examplePrivateStringFieldChild", examplePojo3.getExamplePrivateString());
      Assert.assertEquals(1, examplePojo3.getExamplePrivateInt());
   }

   /**
    * Test for {@linkplain ObjectGeneratorBuilder#register registering} an {@linkplain ObjectGenerator object generator} with the
    * {@linkplain ObjectGenerator#DEFAULT_NAME default name} and then {@linkplain WildstyleGenerator#getObjectGenerator retrieving} it.
    */
   @Test
   public void getObjectGeneratorDefaultName() {
      final ObjectGenerator<ExamplePojo> objectGenerator = WildstyleGenerator
         .createObjectGenerator(ExamplePojo.class)
         .register();

      Assert.assertEquals(objectGenerator, WildstyleGenerator.getObjectGenerator(ExamplePojo.class));
   }

   /**
    * Test for {@linkplain ObjectGeneratorBuilder#register registering} an {@linkplain ObjectGenerator object generator} with a name and
    * then {@linkplain WildstyleGenerator#getObjectGenerator#getObjectGenerator(Class, String) retrieving} it.
    */
   @Test
   public void getObjectGeneratorName() {
      final String name = "name";

      final ObjectGenerator<ExamplePojo> objectGenerator = WildstyleGenerator
         .createObjectGenerator(ExamplePojo.class)
         .setName(name)
         .register();

      Assert.assertEquals(objectGenerator, WildstyleGenerator.getObjectGenerator(ExamplePojo.class, name));
   }

   /**
    * Test for attempting to {@linkplain WildstyleGenerator#getObjectGenerator#getObjectGenerator retrieve} an {@linkplain ObjectGenerator
    * object generator} which has not been {@linkplain ObjectGeneratorBuilder#register registered} with the
    * {@linkplain ObjectGenerator#DEFAULT_NAME default name}.
    */
   @Test
   public void getObjectGeneratorUnregisteredDefaultName() {
      Assert.assertNull(WildstyleGenerator.getObjectGenerator(ExamplePojo.class));
   }

   /**
    * Test for attempting to {@linkplain WildstyleGenerator#getObjectGenerator#getObjectGenerator(Class, String) retrieve} an
    * {@linkplain ObjectGenerator object generator} which has not been {@linkplain ObjectGeneratorBuilder#register registered} with a name.
    */
   @Test
   public void getObjectGeneratorUnregisteredName() {
      Assert.assertNull(WildstyleGenerator.getObjectGenerator(ExamplePojo.class, "name"));
   }

   /**
    * Test for {@linkplain ObjectGeneratorBuilder#register registering} an {@linkplain ObjectGenerator object generator} with the
    * {@linkplain ObjectGenerator#DEFAULT_NAME default name} and then {@linkplain WildstyleGenerator#generate generating} an object with it.
    */
   @Test
   public void generateDefaultName() {
      WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .register();

      Assert.assertNotNull(WildstyleGenerator.generate(ExamplePojo.class));
   }

   /**
    * Test for {@linkplain ObjectGeneratorBuilder#register registering} an {@linkplain ObjectGenerator object generator} with a name and
    * then {@linkplain WildstyleGenerator#generate(Class, String) generating} an object with it.
    */
   @Test
   public void generateName() {
      final String name = "name";

      WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .setName(name)
         .register();

      Assert.assertNotNull(WildstyleGenerator.generate(ExamplePojo.class, name));
   }

   /**
    * Test for attempting to {@linkplain WildstyleGenerator#generate generate} an object with an {@linkplain ObjectGenerator object
    * generator} that has not been {@linkplain ObjectGeneratorBuilder#register registered} with the {@linkplain ObjectGenerator#DEFAULT_NAME
    * default name}.
    */
   @Test
   public void generateUnregisteredDefaultName() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("Did not find a 'class ar.wildstyle.test.ExamplePojo' value generator with the name 'default'.");

      WildstyleGenerator.generate(ExamplePojo.class);
   }

   /**
    * Test for attempting to {@linkplain WildstyleGenerator#generate(Class, String) generate} an object with an {@linkplain ObjectGenerator
    * object generator} that has not been {@linkplain ObjectGeneratorBuilder#register registered} with a name.
    */
   @Test
   public void generateUnregisteredName() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("Did not find a 'class ar.wildstyle.test.ExamplePojo' value generator with the name 'name'.");

      WildstyleGenerator.generate(ExamplePojo.class, "name");
   }

   /**
    * Test for clearing the registry of {@linkplain ObjectGenerator object generators}.
    */
   @Test
   public void clearRegistryTest() {
      WildstyleGenerator.createObjectGenerator(ExamplePojo.class)
         .register();

      Assert.assertNotNull(WildstyleGenerator.getObjectGenerator(ExamplePojo.class));
      Assert.assertNotNull(WildstyleGenerator.generate(ExamplePojo.class));

      WildstyleGenerator.clearRegistry();

      Assert.assertNull(WildstyleGenerator.getObjectGenerator(ExamplePojo.class));

      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("Did not find a 'class ar.wildstyle.test.ExamplePojo' value generator with the name 'default'.");

      WildstyleGenerator.generate(ExamplePojo.class);
   }
}

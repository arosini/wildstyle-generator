package ar.wildstyle.util;

import java.lang.reflect.Field;
import java.util.Arrays;

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
 * {@code ReflectionUtilsTests} contains tests for the {@link ReflectionUtils} class.
 *
 * @author Adam Rosini
 */
public class ReflectionUtilsTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() throws Exception {
      this.object = new ExamplePojo();
      this.stringField = ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_PRIVATE_STRING_FIELD_NAME);
      this.primitiveIntField = ExamplePojo.class.getDeclaredField(ExamplePojo.EXAMPLE_PRIVATE_INT_FIELD_NAME);
      this.stringValue = "exampleValue";
      this.integerValue = 1;
      this.stringValueGenerator = new StringValueGenerator();
      this.integerValueGenerator = new IntegerValueGenerator();
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} with compatible parameters.
    */
   @Test
   public void isCompatibleFieldAndValueGenerator() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.stringField, this.stringValueGenerator));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} with incompatible parameters.
    */
   @Test
   public void isCompatibleFieldAndValueGeneratorIncompatible() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.stringField, this.integerValueGenerator));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} with compatible parameters where the field
    * holds a primitive value.
    */
   @Test
   public void isCompatibleFieldAndValueGeneratorPrimitiveField() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.primitiveIntField, this.integerValueGenerator));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} with incompatible parameters where the
    * field holds a primitive value.
    */
   @Test
   public void isCompatibleFieldAndValueGeneratorPrimitiveFieldNotCompatible() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.primitiveIntField, this.stringValueGenerator));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} where the value generator can generate
    * nulls.
    */
   @Test
   public void isCompatibleFieldAndValueGeneratorCanGenerateNulls() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.stringField, new StringValueGenerator(1)));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} where the fields holds a primitive value
    * and the value generator can generate nulls.
    */
   @Test
   public void isCompatibleFieldAndValueGeneratorPrimitiveFieldCanGenerateNulls() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.primitiveIntField, new StringValueGenerator(1)));
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} with a null field parameter.
    */
   @Test
   public void isCompatibleFieldAndValueGeneratorNullField() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.isCompatible(null, this.stringValueGenerator);
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#isCompatible(Field, ValueGenerator)} with a null value generator parameter.
    */
   @Test
   public void isCompatibleFieldAndValueGeneratorNullValueGenerator() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.isCompatible(this.stringField, (ValueGenerator<?>)null);
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Object)} with compatible parameters.
    */
   @Test
   public void isCompatibleFieldAndValue() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.stringField, this.stringValue));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Object)} with incompatible parameters.
    */
   @Test
   public void isCompatibleFieldAndValueIncompatible() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.stringField, this.integerValue));
   }


   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Object)} with compatible parameters where the field holds a
    * primitive value.
    */
   @Test
   public void isCompatibleFieldAndValuePrimitiveField() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.primitiveIntField, this.integerValue));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Object)} with incompatible parameters where the field holds
    * a primitive value.
    */
   @Test
   public void isCompatibleFieldAndValuePrimitiveFieldIncompatible() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.primitiveIntField, this.stringValue));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Object)} with a null value parameter.
    */
   @Test
   public void isCompatibleFieldAndValueNullValue() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.stringField, (String)null));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Object)} with a field that holds a primitive {@code int}
    * value and a null value parameter .
    */
   @Test
   public void isCompatibleFieldAndValuePrimitiveFieldNullValue() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.primitiveIntField, (Integer)null));
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#isCompatible(Field, Value)} with a null field parameter.
    */
   @Test
   public void isCompatibleFieldAndValueNullField() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.isCompatible(null, this.stringValue);
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Class)} with compatible parameters.
    */
   @Test
   public void isCompatibleFieldAndClass() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.stringField, String.class));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Class)} with incompatible parameters.
    */
   @Test
   public void isCompatibleFieldAndClassIncompatible() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.stringField, Integer.class));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Class)} with compatible parameters where the field holds a
    * primitive value.
    */
   @Test
   public void isCompatibleFieldAndClassPrimitiveField() throws Exception {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.primitiveIntField, Integer.class));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Class)} with incompatible parameters where the field holds a
    * primitive value.
    */
   @Test
   public void isCompatibleFieldAndClassPrimitiveFieldNotCompatible() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.primitiveIntField, String.class));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Class)} with a null class.
    */
   @Test
   public void isCompatibleFieldAndClassNullClass() {
      Assert.assertTrue(ReflectionUtils.isCompatible(this.stringField, (Class<?>)null));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#isCompatible(Field, Class)} with a field that holds a primitive {@code int}
    * value and a null class.
    */
   @Test
   public void isCompatibleFieldAndClassPrimitiveFieldAndNullClass() {
      Assert.assertFalse(ReflectionUtils.isCompatible(this.primitiveIntField, (Class<?>)null));
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#isCompatible(Field, Class)} with a null field parameter.
    */
   @Test
   public void isCompatibleFieldAndClassNullField() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.isCompatible(null, Object.class);
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#setField}.
    */
   @Test
   public void setField() {
      ReflectionUtils.setField(this.object, this.stringField, this.stringValue);

      Assert.assertEquals(this.stringValue, this.object.getExamplePrivateString());
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#setField} with a null value parameter.
    */
   @Test
   public void setFieldNullValue() {
      ReflectionUtils.setField(this.object, this.stringField, null);

      Assert.assertNull(this.object.getExamplePrivateString());
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#setField} with a {@code field} parameter that refers to a primitive type.
    */
   @Test
   public void setFieldPrimitiveField() {
      final int value = 1;
      ReflectionUtils.setField(this.object, this.primitiveIntField, value);

      Assert.assertEquals(value, this.object.getExamplePrivateInt());
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#setField} with a {@code field} parameter that refers to a primitive type and a
    * null value parameter.
    */
   @Test
   public void setFieldPrimitiveFieldNullValue() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.setField(this.object, this.primitiveIntField, null);
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#setField} with a null object parameter.
    */
   @Test
   public void setFieldNullObject() {
      this.expectedException.expect(NullPointerException.class);

      ReflectionUtils.setField(null, this.stringField, this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#setField} with a null field parameter.
    */
   @Test
   public void setFieldNullField() {
      this.expectedException.expect(NullPointerException.class);

      ReflectionUtils.setField(this.object, null, this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#setField} with a field parameter that is not associated with the object
    * parameter's class.
    */
   @Test
   public void setFieldDifferentClass() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.setField(new ExamplePojoParent(), this.stringField, this.stringValue);
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#setField} with a value parameter that is incompatible with the field parameter.
    */
   @Test
   public void setFieldIncompatibleValue() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.setField(this.object, this.stringField, 1);
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance}.
    */
   @Test
   public void newInstance() {
      final ExamplePojo examplePojo = ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(this.stringValue));

      Assert.assertEquals(this.stringValue, examplePojo.getExamplePrivateString());
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} using a default constructor.
    */
   @Test
   public void newInstanceDefaultConstructor() {
      final ExamplePojo examplePojo = ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList());

      Assert.assertNotNull(examplePojo);
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} using a value generator as a constructor argument.
    */
   @Test
   public void newInstanceUsingValueGeneratorArgument() {
      final ExamplePojo examplePojo = ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(this.stringValueGenerator));

      Assert.assertNotNull(examplePojo.getExamplePrivateString());
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} using a subclass instance of a declared constructor argument as an
    * argument.
    */
   @Test
   public void newInstanceUsingSubclassArgument() {
      final ExamplePojo examplePojo = ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(this.object));

      Assert.assertEquals(this.object, examplePojo.getExamplePrivateObject());
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} using an ambiguous argument.
    */
   @Test
   public void newInstanceUsingAmbiguousArgument() {
      this.expectedException.expect(IllegalArgumentException.class);
      this.expectedException.expectMessage("Could not construct an instance of 'class ar.wildstyle.test.ExamplePojo' " +
         "using the resolved list of constructor arguments: [" + this.object + ", 1]");

      ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(this.object, 1));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} using a null value as an argument.
    */
   @Test
   public void newInstanceUsingNullArgument() {
      final ExamplePojo examplePojo = ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(null, 1, 1));

      Assert.assertEquals(Integer.valueOf(1), examplePojo.getExamplePrivateInteger());
      Assert.assertEquals(1, examplePojo.getExamplePrivateInt());
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} using null value as an argument, when the constructor resolution is
    * ambiguous.
    */
   @Test
   public void newInstanceUsingAmbigiousNullArgument() {
      this.object = null;

      this.expectedException.expect(IllegalArgumentException.class);
      this.expectedException.expectMessage("Could not construct an instance of 'class ar.wildstyle.test.ExamplePojo' " +
         "using the resolved list of constructor arguments: [" + this.object + "]");

      ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(this.object));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} where a primitive argument is attempted to be set to null.
    */
   @Test
   public void newInstanceUsingNullPrimitiveArgument() {
      this.expectedException.expect(IllegalArgumentException.class);
      this.expectedException.expectMessage("Could not construct an instance of 'class ar.wildstyle.test.ExamplePojo' " +
         "using the resolved list of constructor arguments: [" + this.object + ", " + this.integerValue + ", null]");

      ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(this.object, this.integerValue, null));
   }

   /**
    * Test for a valid invocation of {@link ReflectionUtils#newInstance} using a null value as an argument where the resolution should not
    * be ambiguous due to only a single constructor existing that can handle the given null value.
    *
    * TODO: Figure out how to fix this. The correct constructor should be found, but it is not.
    */
   @Test
   public void newInstanceUsingNonAmbigiousNullArgument() {
      this.expectedException.expect(IllegalArgumentException.class);
      this.expectedException.expectMessage("Could not construct an instance of 'class ar.wildstyle.test.ExamplePojo' " +
         "using the resolved list of constructor arguments: [" + this.object + ", null]");

      ReflectionUtils.newInstance(ExamplePojo.class, Arrays.asList(this.object, null));
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#newInstance} with a null type.
    */
   @Test
   public void newInstanceNullType() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.newInstance(null, Arrays.asList());
   }

   /**
    * Test for attempting to invoke {@link ReflectionUtils#newInstance} with a null list of arguments.
    */
   @Test
   public void newInstanceNullArgumentList() {
      this.expectedException.expect(AssertionError.class);

      ReflectionUtils.newInstance(ExamplePojo.class, null);
   }

   /**
    * An example {@linkplain Object object}.
    */
   private ExamplePojo object;

   /**
    * An example {@link Field}.
    */
   private Field stringField;

   /**
    * An example {@linkplain Class#isPrimitive primitive} {@link Field}.
    */
   private Field primitiveIntField;

   /**
    * An example {@code String} value.
    */
   private String stringValue;

   /**
    * An example {@code Integer} value.
    */
   private Integer integerValue;

   /**
    * An example {@code String} value generator.
    */
   private ValueGenerator<String> stringValueGenerator;

   /**
    * An example {@code Integer} value generator.
    */
   private ValueGenerator<Integer> integerValueGenerator;

}

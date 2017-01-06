package ar.wildstyle.valuegenerator;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code FloatValueGeneratorTests} contains tests for the {@link FloatValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class FloatValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link FloatValueGenerator#generateValue} using a {@link FloatValueGenerator} which was created with no arguments.
    */
   @Test
   public void generateValue() {
      final FloatValueGenerator floatValueGenerator = new FloatValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Float value = floatValueGenerator.generateValue();
         Assert.assertTrue(value >= FloatValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= FloatValueGenerator.DEFAULT_MAX);
      }
   }

   /**
    * Test for invoking {@link FloatValueGenerator#generateValue} using a {@link FloatValueGenerator} which was created with a value range.
    */
   @Test
   public void generateValueRange() {
      this.testValueRange(-Float.MAX_VALUE, Float.MAX_VALUE);

      this.testValueRange(-Float.MAX_VALUE, -Float.MAX_VALUE / 2);
      this.testValueRange(-Float.MAX_VALUE, -10);
      this.testValueRange(-Float.MAX_VALUE, 0);
      this.testValueRange(-Float.MAX_VALUE, 10);

      this.testValueRange(-20, -10);
      this.testValueRange(-10, -10);

      this.testValueRange(0, 0);
      this.testValueRange(-10, 20);

      this.testValueRange(10, 10);
      this.testValueRange(10, 20);

      this.testValueRange(-100, Float.MAX_VALUE);
      this.testValueRange(0, Float.MAX_VALUE);
      this.testValueRange(100, Float.MAX_VALUE);
      this.testValueRange(Float.MAX_VALUE / 2, Float.MAX_VALUE);
   }

   /**
    * Test for invoking {@link FloatValueGenerator#generateValue} using a {@link FloatValueGenerator} which was created with a null chance.
    */
   @Test
   public void generateValueNullChance() {
      FloatValueGenerator floatValueGenerator = new FloatValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Float value = floatValueGenerator.generateValue();
         Assert.assertTrue(value >= FloatValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= FloatValueGenerator.DEFAULT_MAX);
      }

      floatValueGenerator = new FloatValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Float value = floatValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value >= FloatValueGenerator.DEFAULT_MIN);
            Assert.assertTrue(value <= FloatValueGenerator.DEFAULT_MAX);
         }
      }

      floatValueGenerator = new FloatValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(floatValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link FloatValueGenerator#generateValue} using a {@link FloatValueGenerator} which was created with a value range
    * and a null chance.
    */
   @Test
   public void generateValueRangeAndNullChance() {
      this.testValueRanges(0);
      this.testValueRanges(50);
      this.testValueRanges(100);
   }

   /**
    * Test for invoking {@link FloatValueGenerator#generateValue} using a {@link FloatValueGenerator} which was created with a value range
    * and a chance of generating a {@code null} value.
    */
   @Test
   public void generateValueInRangePossiblyNull() {
      final FloatValueGenerator floatValueGenerator = new FloatValueGenerator(-1, 1, 50);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Float value = floatValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value >= -1);
            Assert.assertTrue(value <= 1);
         }
      }
   }

   /**
    * Test for invoking {@link FloatValueGenerator#generateValue} using a {@link FloatValueGenerator} which was created with a value range
    * and a 100% chance of generating a {@code null} value.
    */
   @Test
   public void generateValueInRangeAlwaysNull() {
      final FloatValueGenerator floatValueGenerator = new FloatValueGenerator(-1, 1, 100);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(floatValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link FloatValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Float.class, new FloatValueGenerator().getValueType());
   }


   /**
    * Test for attempting to create a {@link FloatValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void floatValueGeneratorWithRangeMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new FloatValueGenerator(2, 1);
   }

   /**
    * Test for attempting to create a {@link FloatValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void floatValueGeneratorWithNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new FloatValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link FloatValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void floatValueGeneratorWithNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new FloatValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link FloatValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void floatValueGeneratorWithRangeAndNullChanceMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new FloatValueGenerator(2, 1, 0);
   }

   /**
    * Test for attempting to create a {@link FloatValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void floatValueGeneratorWithRangeAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new FloatValueGenerator(FloatValueGenerator.DEFAULT_MIN, FloatValueGenerator.DEFAULT_MAX, -0.1);
   }

   /**
    * Test for attempting to create a {@link FloatValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void floatValueGeneratorWithRangeAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new FloatValueGenerator(FloatValueGenerator.DEFAULT_MIN, FloatValueGenerator.DEFAULT_MAX, 100.1);
   }

   /**
    * Tests that a {@link FloatValueGenerator} created with the given min and max will properly {@link FloatValueGenerator#generateValue
    * generate} values.
    */
   private void testValueRange(float min, float max) {
      final FloatValueGenerator floatValueGenerator = new FloatValueGenerator(min, max);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Float value = floatValueGenerator.generateValue();
         Assert.assertTrue(value >= min);
         Assert.assertTrue(value <= max);
      }
   }

   /**
    * Tests that a {@link FloatValueGenerator} created with the given min, max and null chance will properly
    * {@link FloatValueGenerator#generateValue generate} values.
    */
   private void testValueRange(float min, float max, double nullChance) {
      final FloatValueGenerator floatValueGenerator = new FloatValueGenerator(min, max, nullChance);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Float value = floatValueGenerator.generateValue();

         if (nullChance == 0) {
            Assert.assertNotNull(value);
         }

         else if (nullChance == 100) {
            Assert.assertNull(value);
         }

         else {
            if (value != null) {
               Assert.assertTrue(value >= min);
               Assert.assertTrue(value <= max);
            }
         }
      }
   }

   /**
    * Tests that a {@link FloatValueGenerator} created with the given null chance will properly {@link FloatValueGenerator#generateValue
    * generate} values for various value ranges.
    */
   private void testValueRanges(double nullChance) {
      this.testValueRange(-Float.MAX_VALUE, Float.MAX_VALUE, nullChance);

      this.testValueRange(-Float.MAX_VALUE, (-Float.MAX_VALUE / 2), nullChance);
      this.testValueRange(-Float.MAX_VALUE, -10, nullChance);
      this.testValueRange(-Float.MAX_VALUE, 0, nullChance);
      this.testValueRange(-Float.MAX_VALUE, 10, nullChance);

      this.testValueRange(-20, -10, nullChance);
      this.testValueRange(-10, -10, nullChance);

      this.testValueRange(0, 0, nullChance);
      this.testValueRange(-10, 20, nullChance);

      this.testValueRange(10, 10, nullChance);
      this.testValueRange(10, 20, nullChance);

      this.testValueRange(-100, Float.MAX_VALUE, nullChance);
      this.testValueRange(0, Float.MAX_VALUE, nullChance);
      this.testValueRange(100, Float.MAX_VALUE, nullChance);
      this.testValueRange((Float.MAX_VALUE / 2), Float.MAX_VALUE, nullChance);
   }

}
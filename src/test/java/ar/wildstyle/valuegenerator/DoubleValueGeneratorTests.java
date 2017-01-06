package ar.wildstyle.valuegenerator;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code DoubleValueGeneratorTests} contains tests for the {@link DoubleValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class DoubleValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link DoubleValueGenerator#generateValue} using a {@link DoubleValueGenerator} which was created with no arguments.
    */
   @Test
   public void generateValue() {
      final DoubleValueGenerator doubleValueGenerator = new DoubleValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Double value = doubleValueGenerator.generateValue();
         Assert.assertTrue(value >= DoubleValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= DoubleValueGenerator.DEFAULT_MAX);
      }
   }

   /**
    * Test for invoking {@link DoubleValueGenerator#generateValue} using a {@link DoubleValueGenerator} which was created with a value
    * range.
    */
   @Test
   public void generateValueRange() {
      this.testValueRange(-Double.MAX_VALUE, Double.MAX_VALUE);

      this.testValueRange(-Double.MAX_VALUE, -Double.MAX_VALUE / 2);
      this.testValueRange(-Double.MAX_VALUE, -10);
      this.testValueRange(-Double.MAX_VALUE, 0);
      this.testValueRange(-Double.MAX_VALUE, 10);

      this.testValueRange(-20, -10);
      this.testValueRange(-10, -10);

      this.testValueRange(0, 0);
      this.testValueRange(-10, 20);

      this.testValueRange(10, 10);
      this.testValueRange(10, 20);

      this.testValueRange(-100, Double.MAX_VALUE);
      this.testValueRange(0, Double.MAX_VALUE);
      this.testValueRange(100, Double.MAX_VALUE);
      this.testValueRange(Double.MAX_VALUE / 2, Double.MAX_VALUE);
   }

   /**
    * Test for invoking {@link DoubleValueGenerator#generateValue} using a {@link DoubleValueGenerator} which was created with a null
    * chance.
    */
   @Test
   public void generateValueNullChance() {
      DoubleValueGenerator doubleValueGenerator = new DoubleValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Double value = doubleValueGenerator.generateValue();
         Assert.assertTrue(value >= DoubleValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= DoubleValueGenerator.DEFAULT_MAX);
      }

      doubleValueGenerator = new DoubleValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Double value = doubleValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value >= DoubleValueGenerator.DEFAULT_MIN);
            Assert.assertTrue(value <= DoubleValueGenerator.DEFAULT_MAX);
         }
      }

      doubleValueGenerator = new DoubleValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(doubleValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link DoubleValueGenerator#generateValue} using a {@link DoubleValueGenerator} which was created with a value range
    * and a null chance.
    */
   @Test
   public void generateValueRangeAndNullChance() {
      this.testValueRanges(0);
      this.testValueRanges(50);
      this.testValueRanges(100);
   }

   /**
    * Test for invoking {@link DoubleValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Double.class, new DoubleValueGenerator().getValueType());
   }


   /**
    * Test for attempting to create a {@link DoubleValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void doubleValueGeneratorWithRangeMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new DoubleValueGenerator(2, 1);
   }

   /**
    * Test for attempting to create a {@link DoubleValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void doubleValueGeneratorWithNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new DoubleValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link DoubleValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void doubleValueGeneratorWithNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new DoubleValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link DoubleValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void doubleValueGeneratorWithRangeAndNullChanceMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new DoubleValueGenerator(2, 1, 0);
   }

   /**
    * Test for attempting to create a {@link DoubleValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void doubleValueGeneratorWithRangeAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new DoubleValueGenerator(DoubleValueGenerator.DEFAULT_MIN, DoubleValueGenerator.DEFAULT_MAX, -0.1);
   }

   /**
    * Test for attempting to create a {@link DoubleValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void doubleValueGeneratorWithRangeAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new DoubleValueGenerator(DoubleValueGenerator.DEFAULT_MIN, DoubleValueGenerator.DEFAULT_MAX, 100.1);
   }

   /**
    * Tests that a {@link DoubleValueGenerator} created with the given minimum value and maximum value will properly
    * {@link DoubleValueGenerator#generateValue generate} values.
    */
   private void testValueRange(double min, double max) {
      final DoubleValueGenerator doubleValueGenerator = new DoubleValueGenerator(min, max);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Double value = doubleValueGenerator.generateValue();
         Assert.assertTrue(value >= min);
         Assert.assertTrue(value <= max);
      }
   }

   /**
    * Tests that a {@link DoubleValueGenerator} created with the given minimum value, maximum value and null chance will properly
    * {@link DoubleValueGenerator#generateValue generate} values.
    */
   private void testValueRange(double min, double max, double nullChance) {
      final DoubleValueGenerator doubleValueGenerator = new DoubleValueGenerator(min, max, nullChance);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Double value = doubleValueGenerator.generateValue();

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
    * Tests that a {@link DoubleValueGenerator} created with the given null chance will properly {@link DoubleValueGenerator#generateValue
    * generate} values for various value ranges.
    */
   private void testValueRanges(double nullChance) {
      this.testValueRange(-Double.MAX_VALUE, Double.MAX_VALUE, nullChance);

      this.testValueRange(-Double.MAX_VALUE, (-Double.MAX_VALUE / 2), nullChance);
      this.testValueRange(-Double.MAX_VALUE, -10, nullChance);
      this.testValueRange(-Double.MAX_VALUE, 0, nullChance);
      this.testValueRange(-Double.MAX_VALUE, 10, nullChance);

      this.testValueRange(-20, -10, nullChance);
      this.testValueRange(-10, -10, nullChance);

      this.testValueRange(0, 0, nullChance);
      this.testValueRange(-10, 20, nullChance);

      this.testValueRange(10, 10, nullChance);
      this.testValueRange(10, 20, nullChance);

      this.testValueRange(-100, Double.MAX_VALUE, nullChance);
      this.testValueRange(0, Double.MAX_VALUE, nullChance);
      this.testValueRange(100, Double.MAX_VALUE, nullChance);
      this.testValueRange((Double.MAX_VALUE / 2), Double.MAX_VALUE, nullChance);
   }

}
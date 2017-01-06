package ar.wildstyle.valuegenerator;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code IntegerValueGeneratorTests} contains tests for the {@link IntegerValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class IntegerValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link IntegerValueGenerator#generateValue} using a {@link IntegerValueGenerator} which was created with no
    * arguments.
    */
   @Test
   public void generateValue() {
      final IntegerValueGenerator integerValueGenerator = new IntegerValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = integerValueGenerator.generateValue();
         Assert.assertTrue(value >= IntegerValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= IntegerValueGenerator.DEFAULT_MAX);
      }
   }

   /**
    * Test for invoking {@link IntegerValueGenerator#generateValue} using a {@link IntegerValueGenerator} which was created with a value
    * range.
    */
   @Test
   public void generateValueRange() {
      this.testValueRange(Integer.MIN_VALUE, Integer.MAX_VALUE);

      this.testValueRange(Integer.MIN_VALUE, Integer.MIN_VALUE / 2);
      this.testValueRange(Integer.MIN_VALUE, -10);
      this.testValueRange(Integer.MIN_VALUE, 0);
      this.testValueRange(Integer.MIN_VALUE, 10);

      this.testValueRange(-20, -10);
      this.testValueRange(-10, -10);

      this.testValueRange(0, 0);
      this.testValueRange(-10, 20);

      this.testValueRange(10, 10);
      this.testValueRange(10, 20);

      this.testValueRange(-100, Integer.MAX_VALUE);
      this.testValueRange(0, Integer.MAX_VALUE);
      this.testValueRange(100, Integer.MAX_VALUE);
      this.testValueRange(Integer.MAX_VALUE / 2, Integer.MAX_VALUE);
   }

   /**
    * Test for invoking {@link IntegerValueGenerator#generateValue} using a {@link IntegerValueGenerator} which was created with a null
    * chance.
    */
   @Test
   public void generateValueNullChance() {
      IntegerValueGenerator integerValueGenerator = new IntegerValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = integerValueGenerator.generateValue();
         Assert.assertTrue(value >= IntegerValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= IntegerValueGenerator.DEFAULT_MAX);
      }

      integerValueGenerator = new IntegerValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = integerValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value >= IntegerValueGenerator.DEFAULT_MIN);
            Assert.assertTrue(value <= IntegerValueGenerator.DEFAULT_MAX);
         }
      }

      integerValueGenerator = new IntegerValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(integerValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link IntegerValueGenerator#generateValue} using a {@link IntegerValueGenerator} which was created with a value
    * range and a null chance.
    */
   @Test
   public void generateValueRangeAndNullChance() {
      this.testValueRanges(0);
      this.testValueRanges(50);
      this.testValueRanges(100);
   }

   /**
    * Test for invoking {@link IntegerValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Integer.class, new IntegerValueGenerator().getValueType());
   }


   /**
    * Test for attempting to create a {@link IntegerValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void integerValueGeneratorWithRangeMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new IntegerValueGenerator(2, 1);
   }

   /**
    * Test for attempting to create a {@link IntegerValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void integerValueGeneratorWithNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new IntegerValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link IntegerValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void integerValueGeneratorWithNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new IntegerValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link IntegerValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void integerValueGeneratorWithRangeAndNullChanceMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new IntegerValueGenerator(2, 1, 0);
   }

   /**
    * Test for attempting to create a {@link IntegerValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void integerValueGeneratorWithRangeAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new IntegerValueGenerator(IntegerValueGenerator.DEFAULT_MIN, IntegerValueGenerator.DEFAULT_MAX, -0.1);
   }

   /**
    * Test for attempting to create a {@link IntegerValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void integerValueGeneratorWithRangeAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new IntegerValueGenerator(IntegerValueGenerator.DEFAULT_MIN, IntegerValueGenerator.DEFAULT_MAX, 100.1);
   }

   /**
    * Tests that a {@link IntegerValueGenerator} created with the given minimum value and maximum value will properly
    * {@link IntegerValueGenerator#generateValue generate} values.
    */
   private void testValueRange(int min, int max) {
      final IntegerValueGenerator integerValueGenerator = new IntegerValueGenerator(min, max);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = integerValueGenerator.generateValue();
         Assert.assertTrue(value >= min);
         Assert.assertTrue(value <= max);
      }
   }

   /**
    * Tests that a {@link IntegerValueGenerator} created with the given minimum value, maximum value and null chance will properly
    * {@link IntegerValueGenerator#generateValue generate} values.
    */
   private void testValueRange(int min, int max, double nullChance) {
      final IntegerValueGenerator integerValueGenerator = new IntegerValueGenerator(min, max, nullChance);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = integerValueGenerator.generateValue();

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
    * Tests that a {@link IntegerValueGenerator} created with the given null chance will properly {@link IntegerValueGenerator#generateValue
    * generate} values for various value ranges.
    */
   private void testValueRanges(double nullChance) {
      this.testValueRange(Integer.MIN_VALUE, Integer.MAX_VALUE, nullChance);

      this.testValueRange(Integer.MIN_VALUE, (Integer.MIN_VALUE / 2), nullChance);
      this.testValueRange(Integer.MIN_VALUE, -10, nullChance);
      this.testValueRange(Integer.MIN_VALUE, 0, nullChance);
      this.testValueRange(Integer.MIN_VALUE, 10, nullChance);

      this.testValueRange(-20, -10, nullChance);
      this.testValueRange(-10, -10, nullChance);

      this.testValueRange(0, 0, nullChance);
      this.testValueRange(-10, 20, nullChance);

      this.testValueRange(10, 10, nullChance);
      this.testValueRange(10, 20, nullChance);

      this.testValueRange(-100, Integer.MAX_VALUE, nullChance);
      this.testValueRange(0, Integer.MAX_VALUE, nullChance);
      this.testValueRange(100, Integer.MAX_VALUE, nullChance);
      this.testValueRange((Integer.MAX_VALUE / 2), Integer.MAX_VALUE, nullChance);
   }

}
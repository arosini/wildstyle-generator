package ar.wildstyle.valuegenerator;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code LongValueGeneratorTests} contains tests for the {@link LongValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class LongValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link LongValueGenerator#generateValue} using a {@link LongValueGenerator} which was created with no arguments.
    */
   @Test
   public void generateValue() {
      final LongValueGenerator longValueGenerator = new LongValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Long value = longValueGenerator.generateValue();
         Assert.assertTrue(value >= LongValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= LongValueGenerator.DEFAULT_MAX);
      }
   }

   /**
    * Test for invoking {@link LongValueGenerator#generateValue} using a {@link LongValueGenerator} which was created with a value range.
    */
   @Test
   public void generateValueRange() {
      this.testValueRange(Long.MIN_VALUE, Long.MAX_VALUE);

      this.testValueRange(Long.MIN_VALUE, Long.MIN_VALUE / 2);
      this.testValueRange(Long.MIN_VALUE, -10);
      this.testValueRange(Long.MIN_VALUE, 0);
      this.testValueRange(Long.MIN_VALUE, 10);

      this.testValueRange(-20, -10);
      this.testValueRange(-10, -10);

      this.testValueRange(0, 0);
      this.testValueRange(-10, 20);

      this.testValueRange(10, 10);
      this.testValueRange(10, 20);

      this.testValueRange(-100, Long.MAX_VALUE);
      this.testValueRange(0, Long.MAX_VALUE);
      this.testValueRange(100, Long.MAX_VALUE);
      this.testValueRange(Long.MAX_VALUE / 2, Long.MAX_VALUE);
   }

   /**
    * Test for invoking {@link LongValueGenerator#generateValue} using a {@link LongValueGenerator} which was created with a null chance.
    */
   @Test
   public void generateValueNeverNull() {
      LongValueGenerator longValueGenerator = new LongValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Long value = longValueGenerator.generateValue();
         Assert.assertTrue(value >= LongValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= LongValueGenerator.DEFAULT_MAX);
      }

      longValueGenerator = new LongValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Long value = longValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value >= LongValueGenerator.DEFAULT_MIN);
            Assert.assertTrue(value <= LongValueGenerator.DEFAULT_MAX);
         }
      }

      longValueGenerator = new LongValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(longValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link LongValueGenerator#generateValue} using a {@link LongValueGenerator} which was created with a value range and
    * a null chance.
    */
   @Test
   public void generateValueRangeAndNullChance() {
      this.testValueRanges(0);
      this.testValueRanges(50);
      this.testValueRanges(100);
   }

   /**
    * Test for invoking {@link LongValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Long.class, new LongValueGenerator().getValueType());
   }


   /**
    * Test for attempting to create a {@link LongValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void longValueGeneratorWithRangeMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new LongValueGenerator(2, 1);
   }

   /**
    * Test for attempting to create a {@link LongValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void longValueGeneratorWithNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new LongValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link LongValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void longValueGeneratorWithNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new LongValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link LongValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void longValueGeneratorWithRangeAndNullChanceMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new LongValueGenerator(2, 1, 0);
   }

   /**
    * Test for attempting to create a {@link LongValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void longValueGeneratorWithRangeAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new LongValueGenerator(LongValueGenerator.DEFAULT_MIN, LongValueGenerator.DEFAULT_MAX, -0.1);
   }

   /**
    * Test for attempting to create a {@link LongValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void longValueGeneratorWithRangeAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new LongValueGenerator(LongValueGenerator.DEFAULT_MIN, LongValueGenerator.DEFAULT_MAX, 100.1);
   }

   /**
    * Tests that a {@link LongValueGenerator} created with the given minimum value and maximum value will properly
    * {@link LongValueGenerator#generateValue generate} values.
    */
   private void testValueRange(long min, long max) {
      final LongValueGenerator longegerValueGenerator = new LongValueGenerator(min, max);

      for (long x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Long value = longegerValueGenerator.generateValue();
         Assert.assertTrue(value >= min);
         Assert.assertTrue(value <= max);
      }
   }

   /**
    * Tests that a {@link LongValueGenerator} created with the given minimum value, maximum value and null chance will properly
    * {@link LongValueGenerator#generateValue generate} values.
    */
   private void testValueRange(long min, long max, double nullChance) {
      final LongValueGenerator longValueGenerator = new LongValueGenerator(min, max, nullChance);

      for (long x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Long value = longValueGenerator.generateValue();

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
    * Tests that a {@link LongValueGenerator} created with the given null chance will properly {@link LongValueGenerator#generateValue
    * generate} values for various value ranges.
    */
   private void testValueRanges(double nullChance) {
      this.testValueRange(Long.MIN_VALUE, Long.MAX_VALUE, nullChance);

      this.testValueRange(Long.MIN_VALUE, (Long.MIN_VALUE / 2), nullChance);
      this.testValueRange(Long.MIN_VALUE, -10, nullChance);
      this.testValueRange(Long.MIN_VALUE, 0, nullChance);
      this.testValueRange(Long.MIN_VALUE, 10, nullChance);

      this.testValueRange(-20, -10, nullChance);
      this.testValueRange(-10, -10, nullChance);

      this.testValueRange(0, 0, nullChance);
      this.testValueRange(-10, 20, nullChance);

      this.testValueRange(10, 10, nullChance);
      this.testValueRange(10, 20, nullChance);

      this.testValueRange(-100, Long.MAX_VALUE, nullChance);
      this.testValueRange(0, Long.MAX_VALUE, nullChance);
      this.testValueRange(100, Long.MAX_VALUE, nullChance);
      this.testValueRange((Long.MAX_VALUE / 2), Long.MAX_VALUE, nullChance);
   }

}
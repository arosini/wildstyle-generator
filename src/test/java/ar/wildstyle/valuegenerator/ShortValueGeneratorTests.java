package ar.wildstyle.valuegenerator;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code ShortValueGeneratorTests} contains tests for the {@link ShortValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class ShortValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link ShortValueGenerator#generateValue} using a {@link ShortValueGenerator} which was created with no arguments.
    */
   @Test
   public void generateValue() {
      final ShortValueGenerator shortValueGenerator = new ShortValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Short value = shortValueGenerator.generateValue();
         Assert.assertTrue(value >= ShortValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= ShortValueGenerator.DEFAULT_MAX);
      }
   }

   /**
    * Test for invoking {@link ShortValueGenerator#generateValue} using a {@link ShortValueGenerator} which was created with a value range.
    */
   @Test
   public void generateValueRange() {
      this.testValueRange(Short.MIN_VALUE, Short.MAX_VALUE);

      this.testValueRange(Short.MIN_VALUE, (short)(Short.MIN_VALUE / 2));
      this.testValueRange(Short.MIN_VALUE, (short)-10);
      this.testValueRange(Short.MIN_VALUE, (short)0);
      this.testValueRange(Short.MIN_VALUE, (short)10);

      this.testValueRange((short)-20, (short)-10);
      this.testValueRange((short)-10, (short)-10);

      this.testValueRange((short)0, (short)0);
      this.testValueRange((short)-10, (short)20);

      this.testValueRange((short)10, (short)10);
      this.testValueRange((short)10, (short)20);

      this.testValueRange((short)-100, Short.MAX_VALUE);
      this.testValueRange((short)0, Short.MAX_VALUE);
      this.testValueRange((short)100, Short.MAX_VALUE);
      this.testValueRange((short)(Short.MAX_VALUE / 2), Short.MAX_VALUE);
   }

   /**
    * Test for invoking {@link ShortValueGenerator#generateValue} using a {@link ShortValueGenerator} which was created with a null chance.
    */
   @Test
   public void generateValueNullChance() {
      ShortValueGenerator shortValueGenerator = new ShortValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Short value = shortValueGenerator.generateValue();
         Assert.assertTrue(value >= ShortValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= ShortValueGenerator.DEFAULT_MAX);
      }

      shortValueGenerator = new ShortValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Short value = shortValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value >= ShortValueGenerator.DEFAULT_MIN);
            Assert.assertTrue(value <= ShortValueGenerator.DEFAULT_MAX);
         }
      }

      shortValueGenerator = new ShortValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(shortValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link ShortValueGenerator#generateValue} using a {@link ShortValueGenerator} which was created with a value range
    * and a null chance.
    */
   @Test
   public void generateValueRangeAndNullChance() {
      this.testValueRanges(0);
   }

   /**
    * Test for invoking {@link ShortValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Short.class, new ShortValueGenerator().getValueType());
   }


   /**
    * Test for attempting to create a {@link ShortValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void shortValueGeneratorWithRangeMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new ShortValueGenerator((short)2, (short)1);
   }

   /**
    * Test for attempting to create a {@link ShortValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void shortValueGeneratorWithNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new ShortValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link ShortValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void shortValueGeneratorWithNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new ShortValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link ShortValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void shortValueGeneratorWithRangeAndNullChanceMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new ShortValueGenerator((short)2, (short)1, 0);
   }

   /**
    * Test for attempting to create a {@link ShortValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void shortValueGeneratorWithRangeAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new ShortValueGenerator(ShortValueGenerator.DEFAULT_MIN, ShortValueGenerator.DEFAULT_MAX, -0.1);
   }

   /**
    * Test for attempting to create a {@link ShortValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void shortValueGeneratorWithRangeAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new ShortValueGenerator(ShortValueGenerator.DEFAULT_MIN, ShortValueGenerator.DEFAULT_MAX, 100.1);
   }

   /**
    * Tests that a {@link ShortValueGenerator} created with the given min and max will properly {@link ShortValueGenerator#generateValue
    * generate} values.
    */
   private void testValueRange(short min, short max) {
      final ShortValueGenerator shortValueGenerator = new ShortValueGenerator(min, max);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Short value = shortValueGenerator.generateValue();
         Assert.assertTrue(value >= min);
         Assert.assertTrue(value <= max);
      }
   }

   /**
    * Tests that a {@link ShortValueGenerator} created with the given min, max and null chance will properly
    * {@link ShortValueGenerator#generateValue generate} values.
    */
   private void testValueRange(short min, short max, double nullChance) {
      final ShortValueGenerator shortValueGenerator = new ShortValueGenerator(min, max, nullChance);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Short value = shortValueGenerator.generateValue();

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
    * Tests that a {@link ShortValueGenerator} created with the given null chance will properly {@link ShortValueGenerator#generateValue
    * generate} values for various value ranges.
    */
   private void testValueRanges(double nullChance) {
      this.testValueRange(Short.MIN_VALUE, Short.MAX_VALUE, nullChance);

      this.testValueRange(Short.MIN_VALUE, (short)(Short.MIN_VALUE / 2), nullChance);
      this.testValueRange(Short.MIN_VALUE, (short)-10, nullChance);
      this.testValueRange(Short.MIN_VALUE, (short)0, nullChance);
      this.testValueRange(Short.MIN_VALUE, (short)10, nullChance);

      this.testValueRange((short)-20, (short)-10, nullChance);
      this.testValueRange((short)-10, (short)-10, nullChance);

      this.testValueRange((short)0, (short)0, nullChance);
      this.testValueRange((short)-10, (short)20, nullChance);

      this.testValueRange((short)10, (short)10, nullChance);
      this.testValueRange((short)10, (short)20, nullChance);

      this.testValueRange((short)-100, Short.MAX_VALUE, nullChance);
      this.testValueRange((short)0, Short.MAX_VALUE, nullChance);
      this.testValueRange((short)100, Short.MAX_VALUE, nullChance);
      this.testValueRange((short)(Short.MAX_VALUE / 2), Short.MAX_VALUE, nullChance);
   }

}
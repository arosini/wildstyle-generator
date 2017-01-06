package ar.wildstyle.valuegenerator;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code DateValueGeneratorTests} contains tests for the {@link DateValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class DateValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link DateValueGenerator#generateValue} using a {@link DateValueGenerator} which was created with no arguments.
    */
   @Test
   public void generateValue() {
      final DateValueGenerator dateValueGenerator = new DateValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Date value = dateValueGenerator.generateValue();
         Assert.assertTrue(value.getTime() >= DateValueGenerator.DEFAULT_MIN_EPOCH_TIME);
         Assert.assertTrue(value.getTime() <= DateValueGenerator.DEFAULT_MAX_EPOCH_TIME);
      }
   }

   /**
    * Test for invoking {@link DateValueGenerator#generateValue} using a {@link DateValueGenerator} which was created with an Epoch time
    * range.
    */
   @Test
   public void generateValueEpochTimeRange() {
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
    * Test for invoking {@link DateValueGenerator#generateValue} using a {@link DateValueGenerator} which was created with a null chance.
    */
   @Test
   public void generateValueNullChance() {
      DateValueGenerator dateValueGenerator = new DateValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Date value = dateValueGenerator.generateValue();

         Assert.assertTrue(value.getTime() >= DateValueGenerator.DEFAULT_MIN_EPOCH_TIME);
         Assert.assertTrue(value.getTime() <= DateValueGenerator.DEFAULT_MAX_EPOCH_TIME);
      }

      dateValueGenerator = new DateValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Date value = dateValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value.getTime() >= DateValueGenerator.DEFAULT_MIN_EPOCH_TIME);
            Assert.assertTrue(value.getTime() <= DateValueGenerator.DEFAULT_MAX_EPOCH_TIME);
         }
      }

      dateValueGenerator = new DateValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Date value = dateValueGenerator.generateValue();
         Assert.assertNull(value);
      }
   }

   /**
    * Test for invoking {@link DateValueGenerator#generateValue} using a {@link DateValueGenerator} which was created with an Epoch time
    * range and a null chance.
    */
   @Test
   public void generateValueEpochTimeRangeAndNullChanceNeverNull() {
      this.testValueRanges(0);
      this.testValueRanges(50);
      this.testValueRanges(100);
   }

   /**
    * Test for invoking {@link DateValueGenerator#getValueClass}.
    */
   @Test
   public void getValueType() {
      final DateValueGenerator dateValueGenerator = new DateValueGenerator();
      Assert.assertEquals(Date.class, dateValueGenerator.getValueType());
   }

   /**
    * Test for attempting to create a {@link DateValueGenerator} with a minimum Epoch time that is greater than the maximum Epoch time.
    */
   @Test
   public void dateValueGeneratorMinEpochTimeGreaterThanMaxEpochTime() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'minEpochTime' parameter must be less than or equal to the 'maxEpochTime' parameter.");

      new DateValueGenerator(1, 0);
   }

   /**
    * Test for attempting to create a {@link DateValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void dateValueGeneratorWithNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new DateValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link DateValueGenerator} with a null chance that is greater than 100.
    */
   @Test
   public void dateValueGeneratorWithNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new DateValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link DateValueGenerator} with a minimum Epoch time that is greater than the maximum Epoch time and a
    * valid null chance.
    */
   @Test
   public void dateValueGeneratorMinEpochTimeGreaterThanMaxEpochTimeAndNullChance() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'minEpochTime' parameter must be less than or equal to the 'maxEpochTime' parameter.");

      new DateValueGenerator(1, 0, DateValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Test for attempting to create a {@link DateValueGenerator} with an Epoch time range and a null chance that is less than 0.
    */
   @Test
   public void dateValueGeneratorWithEpochTimeRangeAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new DateValueGenerator(DateValueGenerator.DEFAULT_MIN_EPOCH_TIME, DateValueGenerator.DEFAULT_MAX_EPOCH_TIME, -0.1);
   }

   /**
    * Test for attempting to create a {@link DateValueGenerator} with an Epoch time range and a null chance that is greater than 100.
    */
   @Test
   public void dateValueGeneratorWithEpochTimeRangeAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new DateValueGenerator(DateValueGenerator.DEFAULT_MIN_EPOCH_TIME, DateValueGenerator.DEFAULT_MAX_EPOCH_TIME, 100.1);
   }

   /**
    * Tests that a {@link DateValueGenerator} created with the given minimum Epoch time and maximum Epoch time will properly
    * {@link DateValueGenerator#generateValue generate} values.
    */
   private void testValueRange(long minEpochTime, long maxEpochTime) {
      final DateValueGenerator dateValueGenerator = new DateValueGenerator(minEpochTime, maxEpochTime);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Date value = dateValueGenerator.generateValue();
         Assert.assertTrue(value.getTime() >= minEpochTime);
         Assert.assertTrue(value.getTime() <= maxEpochTime);
      }
   }

   /**
    * Tests that a {@link DateValueGenerator} created with the given minimum Epoch time, maximum Epoch time and null chance will properly
    * {@link DateValueGenerator#generateValue generate} values.
    */
   private void testValueRange(long minEpochTime, long maxEpochTime, double nullChance) {
      final DateValueGenerator dateValueGenerator = new DateValueGenerator(minEpochTime, maxEpochTime, nullChance);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Date value = dateValueGenerator.generateValue();

         if (nullChance == 0) {
            Assert.assertNotNull(value);
         }

         else if (nullChance == 100) {
            Assert.assertNull(value);
         }

         else {
            if (value != null) {
               Assert.assertTrue(value.getTime() >= minEpochTime);
               Assert.assertTrue(value.getTime() <= maxEpochTime);
            }
         }
      }
   }

   /**
    * Tests that a {@link DateValueGenerator} created with the given null chance will properly {@link DateValueGenerator#generateValue
    * generate} values for various Epoch time ranges.
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

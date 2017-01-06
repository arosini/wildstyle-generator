package ar.wildstyle.valuegenerator;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code ByteValueGeneratorTests} contains tests for the {@link ByteValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class ByteValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link ByteValueGenerator#generateValue} using a {@link ByteValueGenerator} which was created with no arguments.
    */
   @Test
   public void generateValue() {
      final ByteValueGenerator byteValueGenerator = new ByteValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Byte value = byteValueGenerator.generateValue();
         Assert.assertTrue(value >= ByteValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= ByteValueGenerator.DEFAULT_MAX);
      }
   }

   /**
    * Test for invoking {@link ByteValueGenerator#generateValue} using a {@link ByteValueGenerator} which was created with a value range.
    */
   @Test
   public void generateValueInRange() {
      this.testValueRange(Byte.MIN_VALUE, Byte.MAX_VALUE);

      this.testValueRange(Byte.MIN_VALUE, (byte)(Byte.MIN_VALUE / 2));
      this.testValueRange(Byte.MIN_VALUE, (byte)-10);
      this.testValueRange(Byte.MIN_VALUE, (byte)0);
      this.testValueRange(Byte.MIN_VALUE, (byte)10);

      this.testValueRange((byte)-20, (byte)-10);
      this.testValueRange((byte)-10, (byte)-10);

      this.testValueRange((byte)0, (byte)0);
      this.testValueRange((byte)-10, (byte)20);

      this.testValueRange((byte)10, (byte)10);
      this.testValueRange((byte)10, (byte)20);

      this.testValueRange((byte)-100, Byte.MAX_VALUE);
      this.testValueRange((byte)0, Byte.MAX_VALUE);
      this.testValueRange((byte)100, Byte.MAX_VALUE);
      this.testValueRange((byte)(Byte.MAX_VALUE / 2), Byte.MAX_VALUE);
   }

   /**
    * Test for invoking {@link ByteValueGenerator#generateValue} using a {@link ByteValueGenerator} which was created with a null chance.
    */
   @Test
   public void generateValueNullChance() {
      ByteValueGenerator byteValueGenerator = new ByteValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Byte value = byteValueGenerator.generateValue();
         Assert.assertTrue(value >= ByteValueGenerator.DEFAULT_MIN);
         Assert.assertTrue(value <= ByteValueGenerator.DEFAULT_MAX);
      }

      byteValueGenerator = new ByteValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Byte value = byteValueGenerator.generateValue();

         if (value != null) {
            Assert.assertTrue(value >= ByteValueGenerator.DEFAULT_MIN);
            Assert.assertTrue(value <= ByteValueGenerator.DEFAULT_MAX);
         }
      }

      byteValueGenerator = new ByteValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(byteValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link ByteValueGenerator#generateValue} using a {@link ByteValueGenerator} which was created with a value range and
    * a null chance.
    */
   @Test
   public void generateValueRangeAndNullChance() {
      this.testValueRanges(0);
      this.testValueRanges(50);
      this.testValueRanges(100);
   }

   /**
    * Test for invoking {@link ByteValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Byte.class, new ByteValueGenerator().getValueType());
   }


   /**
    * Test for attempting to create a {@link ByteValueGenerator} with a minimum value that is greater than the maximum value.
    */
   @Test
   public void byteValueGeneratorWithRangeMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new ByteValueGenerator((byte)2, (byte)1);
   }

   /**
    * Test for attempting to create a {@link ByteValueGenerator} with a null chance that is less than 0%.
    */
   @Test
   public void byteValueGeneratorWithNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new ByteValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link ByteValueGenerator} with a null chance that is greater than 100%.
    */
   @Test
   public void byteValueGeneratorWithNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new ByteValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link ByteValueGenerator} with a minimum value that is greater than the maximum value and a valid
    * null chance.
    */
   @Test
   public void byteValueGeneratorWithRangeMinGreaterThanMaxAndNullChance() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'min' parameter must be less than or equal to the 'max' parameter.");

      new ByteValueGenerator((byte)2, (byte)1, 0);
   }

   /**
    * Test for attempting to create a {@link ByteValueGenerator} with a valid range and a null chance that is less than 0%.
    */
   @Test
   public void byteValueGeneratorWithRangeAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new ByteValueGenerator(ByteValueGenerator.DEFAULT_MIN, ByteValueGenerator.DEFAULT_MAX, -0.1);
   }

   /**
    * Test for attempting to create a {@link ByteValueGenerator} with a valid range and a null chance that is greater than 100%.
    */
   @Test
   public void byteValueGeneratorWithRangeAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new ByteValueGenerator(ByteValueGenerator.DEFAULT_MIN, ByteValueGenerator.DEFAULT_MAX, 100.1);
   }

   /**
    * Tests that a {@link ByteValueGenerator} created with the given minimum value and maximum value will properly
    * {@link ByteValueGenerator#generateValue generate} values.
    */
   private void testValueRange(byte min, byte max) {
      final ByteValueGenerator byteValueGenerator = new ByteValueGenerator(min, max);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Byte value = byteValueGenerator.generateValue();
         Assert.assertTrue(value >= min);
         Assert.assertTrue(value <= max);
      }
   }

   /**
    * Tests that a {@link ByteValueGenerator} created with the given minimum value, maximum value and null chance will properly
    * {@link ByteValueGenerator#generateValue generate} values.
    */
   private void testValueRange(byte min, byte max, double nullChance) {
      final ByteValueGenerator byteValueGenerator = new ByteValueGenerator(min, max, nullChance);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Byte value = byteValueGenerator.generateValue();

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
    * Tests that a {@link ByteValueGenerator} created with the given null chance will properly {@link ByteValueGenerator#generateValue
    * generate} values for various value ranges.
    */
   private void testValueRanges(double nullChance) {
      this.testValueRange(Byte.MIN_VALUE, Byte.MAX_VALUE, nullChance);

      this.testValueRange(Byte.MIN_VALUE, (byte)(Byte.MIN_VALUE / 2), nullChance);
      this.testValueRange(Byte.MIN_VALUE, (byte)-10, nullChance);
      this.testValueRange(Byte.MIN_VALUE, (byte)0, nullChance);
      this.testValueRange(Byte.MIN_VALUE, (byte)10, nullChance);

      this.testValueRange((byte)-20, (byte)-10, nullChance);
      this.testValueRange((byte)-10, (byte)-10, nullChance);

      this.testValueRange((byte)0, (byte)0, nullChance);
      this.testValueRange((byte)-10, (byte)20, nullChance);

      this.testValueRange((byte)10, (byte)10, nullChance);
      this.testValueRange((byte)10, (byte)20, nullChance);

      this.testValueRange((byte)-100, Byte.MAX_VALUE, nullChance);
      this.testValueRange((byte)0, Byte.MAX_VALUE, nullChance);
      this.testValueRange((byte)100, Byte.MAX_VALUE, nullChance);
      this.testValueRange((byte)(Byte.MAX_VALUE / 2), Byte.MAX_VALUE, nullChance);
   }

}
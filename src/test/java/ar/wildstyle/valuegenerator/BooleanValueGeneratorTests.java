package ar.wildstyle.valuegenerator;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code BooleanValueGeneratorTests} contains tests for the {@link BooleanValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class BooleanValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link BooleanValueGenerator#generateValue} using a {@link BooleanValueGenerator} which was created with no
    * arguments.
    */
   @Test
   public void generateValue() {
      final BooleanValueGenerator booleanValueGenerator = new BooleanValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNotNull(booleanValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link BooleanValueGenerator#generateValue} using a {@link BooleanValueGenerator} which was created with a true
    * chance.
    */
   @Test
   public void generateValueTrueChance() {
      BooleanValueGenerator booleanValueGenerator = new BooleanValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertFalse(booleanValueGenerator.generateValue());
      }

      booleanValueGenerator = new BooleanValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNotNull(booleanValueGenerator.generateValue());
      }

      booleanValueGenerator = new BooleanValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertTrue(booleanValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link BooleanValueGenerator#generateValue} using a {@link BooleanValueGenerator} which was created with true chance
    * and null chance parameters.
    */
   @Test
   public void generateValueTrueChanceAndNullChance() {
      this.testTrueChanceAndNullChance(0, 0);
      this.testTrueChanceAndNullChance(0, 50);
      this.testTrueChanceAndNullChance(0, 100);
      this.testTrueChanceAndNullChance(50, 0);
      this.testTrueChanceAndNullChance(50, 50);
      this.testTrueChanceAndNullChance(50, 100);
      this.testTrueChanceAndNullChance(100, 0);
      this.testTrueChanceAndNullChance(100, 50);
      this.testTrueChanceAndNullChance(100, 100);
   }

   /**
    * Test for invoking {@link BooleanValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Boolean.class, new BooleanValueGenerator().getValueType());
   }

   /**
    * Test for attempting to create a {@link BooleanValueGenerator} with a true chance that is less than 0%.
    */
   @Test
   public void booleanValueGeneratorWithTrueChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'trueChance' parameter must be greater than or equal to 0.");

      new BooleanValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link BooleanValueGenerator} with a true chance that is greater than 100%.
    */
   @Test
   public void booleanValueGeneratorWithTrueChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'trueChance' parameter must be less than or equal to 100.");

      new BooleanValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link BooleanValueGenerator} with a true chance that is less than 0% and a valid null chance.
    */
   @Test
   public void booleanValueGeneratorWithTrueChanceLessThan0AndNullChance() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'trueChance' parameter must be greater than or equal to 0.");

      new BooleanValueGenerator(-0.1, BooleanValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Test for attempting to create a {@link BooleanValueGenerator} with a true chance that is greater than 100% and a valid null chance.
    */
   @Test
   public void booleanValueGeneratorWithTrueChanceGreaterThan100AndNullChance() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'trueChance' parameter must be less than or equal to 100.");

      new BooleanValueGenerator(100.1, BooleanValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Test for attempting to create a {@link BooleanValueGenerator} with a valid true chance and a null chance that is less than 0%.
    */
   @Test
   public void booleanValueGeneratorWithTrueChanceAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new BooleanValueGenerator(BooleanValueGenerator.DEFAULT_TRUE_CHANCE, -0.1);
   }

   /**
    * Test for attempting to create a {@link BooleanValueGenerator} with a valid true chance and a null chance that is greater than 100%.
    */
   @Test
   public void booleanValueGeneratorWithTrueChanceAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new BooleanValueGenerator(BooleanValueGenerator.DEFAULT_TRUE_CHANCE, 100.1);
   }

   /**
    * Tests that a {@link BooleanValueGenerator} created with the given true chance and null chance properly
    * {@linkplain BooleanValueGenerator#generateValue generates} values.
    *
    * @pre trueChance >= 0
    * @pre trueChance <= 100
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   private void testTrueChanceAndNullChance(double trueChance, double nullChance) {
      assert trueChance >= 0;
      assert trueChance <= 100;
      assert nullChance >= 0;
      assert nullChance <= 100;

      final BooleanValueGenerator booleanValueGenerator = new BooleanValueGenerator(trueChance, nullChance);
      final Boolean value = booleanValueGenerator.generateValue();

      if (nullChance == 100) {
         Assert.assertNull(value);
      }

      else if (nullChance == 0) {
         Assert.assertNotNull(value);
      }

      if (value != null) {
         if (trueChance == 100) {
            Assert.assertTrue(value);
         }

         else if (trueChance == 0) {
            Assert.assertFalse(value);
         }
      }
   }

}

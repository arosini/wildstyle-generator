package ar.wildstyle.valuegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code StringValueGeneratorTests} contains tests for the {@link StringValueGenerator} class.
 *
 * TODO: Cover all cases for all overloaded constructors. Probably unnecessary, but technically correct.
 *
 * @author Adam Rosini
 */
public class StringValueGeneratorTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() {
      this.minLength = 10;
      this.maxLength = 20;
      this.allowableCharacters = Arrays.asList('a', 'b', 'c', '$', '\t');
      this.nullChance = 50;
   }

   /**
    * Test for invoking {@link StringValueGenerator#generateValue} using a {@link StringValueGenerator} which was created with no arguments.
    */
   @Test
   public void generateValue() {
      final StringValueGenerator stringValueGenerator = new StringValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final String value = stringValueGenerator.generateValue();
         Assert.assertTrue(value.length() >= StringValueGenerator.DEFAULT_MIN_LENGTH);
         Assert.assertTrue(value.length() <= StringValueGenerator.DEFAULT_MAX_LENGTH);

         for (final Character c : value.toCharArray()) {
            Assert.assertTrue(StringValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS.contains(c));
         }
      }
   }

   /**
    * Test for invoking {@link StringValueGenerator#generateValue} using a {@link StringValueGenerator} which was created with a length
    * range.
    */
   @Test
   public void generateValueLengthRange() {
      final StringValueGenerator stringValueGenerator =
         new StringValueGenerator(this.minLength, this.maxLength);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final String value = stringValueGenerator.generateValue();
         Assert.assertTrue(value.length() >= this.minLength);
         Assert.assertTrue(value.length() <= this.maxLength);

         for (final Character c : value.toCharArray()) {
            Assert.assertTrue(StringValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS.contains(c));
         }
      }
   }

   /**
    * Test for invoking {@link StringValueGenerator#generateValue} using a {@link StringValueGenerator} which was created with a list of
    * allowable characters.
    */
   @Test
   public void generateValueAllowableCharacters() {
      final StringValueGenerator stringValueGenerator = new StringValueGenerator(this.allowableCharacters);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final String value = stringValueGenerator.generateValue();
         Assert.assertTrue(value.length() >= StringValueGenerator.DEFAULT_MIN_LENGTH);
         Assert.assertTrue(value.length() <= StringValueGenerator.DEFAULT_MAX_LENGTH);

         for (final Character c : value.toCharArray()) {
            Assert.assertTrue(this.allowableCharacters.contains(c));
         }
      }
   }

   /**
    * Test for invoking {@link StringValueGenerator#generateValue} using a {@link StringValueGenerator} which never generates a null value.
    */
   @Test
   public void generateValueNeverNull() {
      final StringValueGenerator stringValueGenerator = new StringValueGenerator(0);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final String value = stringValueGenerator.generateValue();
         Assert.assertTrue(value.length() >= StringValueGenerator.DEFAULT_MIN_LENGTH);
         Assert.assertTrue(value.length() <= StringValueGenerator.DEFAULT_MAX_LENGTH);

         for (final Character c : value.toCharArray()) {
            Assert.assertTrue(StringValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS.contains(c));
         }
      }
   }

   /**
    * Test for invoking {@link StringValueGenerator#generateValue} using a {@link StringValueGenerator} which always generates a null value.
    */
   @Test
   public void generateValueAlwaysNull() {
      final StringValueGenerator stringValueGenerator = new StringValueGenerator(100);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         Assert.assertNull(stringValueGenerator.generateValue());
      }
   }

   /**
    * Test for invoking {@link StringValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(String.class, new StringValueGenerator().getValueType());
   }

   /**
    * Test for attempting to create a {@link StringValueGenerator} with a minimum length that is less than 0.
    */
   @Test
   public void stringValueGeneratorMinLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException
         .expectMessage("The 'minLength' parameter must be greater than or equal to 0.");

      new StringValueGenerator(-1, this.maxLength, this.allowableCharacters, this.nullChance);
   }

   /**
    * Test for attempting to create a {@link StringValueGenerator} with a maximum length that is less than 1.
    */
   @Test
   public void stringValueGeneratorMaxLessThan1() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException
         .expectMessage("The 'maxLength' parameter must be greater than or equal to 1.");

      new StringValueGenerator(this.minLength, 0, this.allowableCharacters, this.nullChance);
   }

   /**
    * Test for attempting to create a {@link StringValueGenerator} with a minimum length that is greater than the maximum length.
    */
   @Test
   public void stringValueGeneratorMinGreaterThanMax() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage(
         "The 'minLength' parameter must be less than or equal to the 'maxLength' parameter.");

      new StringValueGenerator(2, 1, this.allowableCharacters, this.nullChance);
   }

   /**
    * Test for attempting to create a {@link StringValueGenerator} with null list of allowable characters.
    */
   @Test
   public void stringValueGeneratorNullAllowableCharacters() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'allowableCharacters' parameter cannot be null.");

      new StringValueGenerator(this.minLength, this.maxLength, null, this.nullChance);
   }

   /**
    * Test for attempting to create a {@link StringValueGenerator} with an empty list of allowable characters.
    */
   @Test
   public void stringValueGeneratorEmptyAllowableCharacters() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'allowableCharacters' parameter cannot be empty.");

      new StringValueGenerator(this.minLength, this.maxLength, new ArrayList<Character>(), this.nullChance);
   }

   /**
    * Test for attempting to create a {@link StringValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void stringValueGeneratorNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new StringValueGenerator(this.minLength, this.maxLength, this.allowableCharacters, -0.1);
   }

   /**
    * Test for attempting to create a {@link StringValueGenerator} with a null chance that is less than 0.
    */
   @Test
   public void stringValueGeneratorNullChanceLessGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new StringValueGenerator(this.minLength, this.maxLength, this.allowableCharacters, 100.1);
   }

   /**
    * An example minimum length value.
    */
   private int minLength;

   /**
    * An example maximum length value.
    */
   private int maxLength;

   /**
    * An example list of characters.
    */
   private List<Character> allowableCharacters;

   /**
    * An example null chance value.
    */
   private double nullChance;

}

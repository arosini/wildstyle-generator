package ar.wildstyle.valuegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code CharacterValueGeneratorTests} contains tests for the {@link CharacterValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class CharacterValueGeneratorTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() {
      this.allowableCharacters = Arrays.asList('a', 'b', 'c', '$', '\t');
   }

   /**
    * Test for invoking {@link CharacterValueGenerator#generateValue} using a {@link CharacterValueGenerator} which was created with no
    * arguments.
    */
   @Test
   public void generateValue() {
      final CharacterValueGenerator characterValueGenerator = new CharacterValueGenerator();

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         Assert.assertTrue(StringValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS.contains(value));
      }
   }

   /**
    * Test for invoking {@link CharacterValueGenerator#generateValue} using a {@link CharacterValueGenerator} which was created with a list
    * of allowable characters.
    */
   @Test
   public void generateValueAllowableCharacters() {
      final CharacterValueGenerator characterValueGenerator = new CharacterValueGenerator(this.allowableCharacters);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         Assert.assertTrue(this.allowableCharacters.contains(value));
      }
   }

   /**
    * Test for invoking {@link CharacterValueGenerator#generateValue} using a {@link CharacterValueGenerator} which was created a null
    * chance.
    */
   @Test
   public void generateValueNullChance() {
      CharacterValueGenerator characterValueGenerator = new CharacterValueGenerator(0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         Assert.assertTrue(StringValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS.contains(value));
      }

      characterValueGenerator = new CharacterValueGenerator(50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         if (value != null) {
            Assert.assertTrue(StringValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS.contains(value));
         }
      }

      characterValueGenerator = new CharacterValueGenerator(100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         Assert.assertNull(value);
      }
   }

   /**
    * Test for invoking {@link CharacterValueGenerator#generateValue} using a {@link CharacterValueGenerator} which was created with a list
    * of allowable characters and a null chance.
    */
   @Test
   public void generateValueAllowableCharactersAndNullChance() {
      CharacterValueGenerator characterValueGenerator = new CharacterValueGenerator(this.allowableCharacters, 0);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         Assert.assertNotNull(value);
         Assert.assertTrue(this.allowableCharacters.contains(value));
      }

      characterValueGenerator = new CharacterValueGenerator(this.allowableCharacters, 50);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         if (value != null) {
            Assert.assertTrue(this.allowableCharacters.contains(value));
         }
      }

      characterValueGenerator = new CharacterValueGenerator(this.allowableCharacters, 100);
      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Character value = characterValueGenerator.generateValue();
         Assert.assertNull(value);
      }
   }

   /**
    * Test for invoking {@link CharacterValueGenerator#getValueClass}.
    */
   @Test
   public void getValueClass() {
      Assert.assertEquals(Character.class, new CharacterValueGenerator().getValueType());
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with a null list of allowable characters.
    */
   @Test
   public void characterValueGeneratorNullAllowableCharacters() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'allowableCharacters' parameter cannot be null.");

      new CharacterValueGenerator(null);
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with an empty list of allowable characters.
    */
   @Test
   public void characterValueGeneratorEmptyAllowableCharacters() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'allowableCharacters' parameter cannot be empty.");

      new CharacterValueGenerator(new ArrayList<Character>());
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with a null chance that is less than 0%.
    */
   @Test
   public void characterValueGeneratorNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new CharacterValueGenerator(-0.1);
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with a null chance that is greater than 100%.
    */
   @Test
   public void characterValueGeneratorNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new CharacterValueGenerator(100.1);
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with a null list of allowable characters and a valid null chance.
    */
   @Test
   public void characterValueGeneratorNullAllowableCharactersAndNullChance() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'allowableCharacters' parameter cannot be null.");

      new CharacterValueGenerator(null, CharacterValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with an empty list of allowable characters and a valid null chance.
    */
   @Test
   public void characterValueGeneratorEmptyAllowableCharactersAndNullChance() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'allowableCharacters' parameter cannot be empty.");

      new CharacterValueGenerator(new ArrayList<Character>(), CharacterValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with a valid list of allowable characters and a null chance that is
    * less than 0%.
    */
   @Test
   public void characterValueGeneratorAllowableCharactersAndNullChanceLessThan0() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be greater than or equal to 0.");

      new CharacterValueGenerator(CharacterValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS, -0.1);
   }

   /**
    * Test for attempting to create a {@link CharacterValueGenerator} with a valid list of allowable characters and a null chance that is
    * greater than 100%.
    */
   @Test
   public void characterValueGeneratorAllowableCharactersAndNullChanceGreaterThan100() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'nullChance' parameter must be less than or equal to 100.");

      new CharacterValueGenerator(CharacterValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS, 100.1);
   }

   /**
    * An example list of characters.
    */
   private List<Character> allowableCharacters;

}

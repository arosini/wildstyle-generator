package ar.wildstyle.valuegenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code SetBasedValueGeneratorTests} contains tests for the {@link SetBasedValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class SetBasedValueGeneratorTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() {
      this.values = new HashSet<>();
      this.values.add(0);
      this.values.add(1);
      this.values.add(2);
      this.values.add(3);
   }

   /**
    * Test for invoking {@link SetBasedValueGenerator#generateValue} with a {@link SetBasedValueGenerator} which was created with unique
    * selections.
    */
   @Test
   public void generateValueUniqueSelections() {
      final SetBasedValueGenerator<Integer> setBasedValueGenerator = new SetBasedValueGenerator<>(Integer.class, this.values, true);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Set<Integer> usedValues = new HashSet<>();

         for (int i = 0; i < this.values.size(); i++) {
            final Integer value = setBasedValueGenerator.generateValue();

            Assert.assertTrue(value >= 0);
            Assert.assertTrue(value <= 3);
            Assert.assertTrue(usedValues.add(value));
         }
      }
   }

   /**
    * Test for invoking {@link SetBasedValueGenerator#generateValue} with a {@link SetBasedValueGenerator} which was created with non-unique
    * selections.
    */
   @Test
   public void generateValueNonUniqueSelections() {
      final SetBasedValueGenerator<Integer> setBasedValueGenerator = new SetBasedValueGenerator<>(Integer.class, this.values, false);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = setBasedValueGenerator.generateValue();

         Assert.assertTrue(value >= 0);
         Assert.assertTrue(value <= 3);
      }
   }

   /**
    * Test for invoking {@link SetBasedValueGenerator#getValueType} with a {@link SetBasedValueGenerator} which was created to generate
    * {@link Integer} values.
    */
   @Test
   public void getValueType() {
      final SetBasedValueGenerator<Integer> setBasedValueGenerator = new SetBasedValueGenerator<>(Integer.class, this.values, false);
      Assert.assertEquals(Integer.class, setBasedValueGenerator.getValueType());
   }

   /**
    * Test for attempting to create a {@code SetBasedValueGenerator} with a null value type.
    */
   @Test
   public void setBasedValueGeneratorNullValueType() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'valueType' parameter cannot be null.");

      final Class<String> valueType = null;
      new SetBasedValueGenerator<String>(valueType, new HashSet<String>(Arrays.asList("1", "2")), true);
   }

   /**
    * Test for attempting to create a {@code SetBasedValueGenerator} with a null values.
    */
   @Test
   public void setBasedValueGeneratorNullValues() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'values' parameter cannot be null.");

      new SetBasedValueGenerator<String>(String.class, null, true);
   }

   /**
    * Test for attempting to create a {@code SetBasedValueGenerator} with an empty values.
    */
   @Test
   public void setBasedValueGeneratorEmptyValues() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'values' parameter cannot be empty.");

      new SetBasedValueGenerator<String>(String.class, new HashSet<String>(), true);
   }

   /**
    * An example set of values.
    */
   private Set<Integer> values;

}

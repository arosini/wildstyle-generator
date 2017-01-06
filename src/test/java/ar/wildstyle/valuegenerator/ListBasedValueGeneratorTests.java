package ar.wildstyle.valuegenerator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code ListBasedValueGeneratorTests} contains tests for the {@link ListBasedValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class ListBasedValueGeneratorTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() {
      this.values = new ArrayList<>();
      this.values.add(0);
      this.values.add(1);
      this.values.add(2);
      this.values.add(3);
   }

   /**
    * Test for invoking {@link ListBasedValueGenerator#generateValue} with a {@link ListBasedValueGenerator} which was created with repeat
    * selections.
    */
   @Test
   public void generateValueRepeatSelections() {
      final ListBasedValueGenerator<Integer> listBasedValueGenerator = new ListBasedValueGenerator<>(Integer.class, this.values, true);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = listBasedValueGenerator.generateValue();
         Assert.assertEquals(Integer.valueOf(x % this.values.size()), value);
      }
   }

   /**
    * Test for invoking {@link ListBasedValueGenerator#generateValue} with a {@link ListBasedValueGenerator} which was created with
    * non-repeating selections.
    */
   @Test
   public void generateValueNonRepeatingSelections() {
      final ListBasedValueGenerator<Integer> listBasedValueGenerator = new ListBasedValueGenerator<>(Integer.class, this.values, false);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Integer value = listBasedValueGenerator.generateValue();

         if (x < this.values.size()) {
            Assert.assertEquals(Integer.valueOf(x), value);
         }
         else {
            Assert.assertNull(value);
         }
      }
   }

   /**
    * Test for invoking {@link ListBasedValueGenerator#getValueType} with a {@link ListBasedValueGenerator} which was created to generate
    * {@link Integer} values.
    */
   @Test
   public void getValueType() {
      final ListBasedValueGenerator<Integer> listBasedValueGenerator = new ListBasedValueGenerator<>(Integer.class, this.values, false);

      Assert.assertEquals(Integer.class, listBasedValueGenerator.getValueType());
   }

   /**
    * An example list of values.
    */
   private List<Integer> values;

}

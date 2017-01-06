package ar.wildstyle.valuegenerator;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;
import ar.wildstyle.test.ExamplePojo.ExamplePojoEnum;

/**
 * {@code EnumValueGeneratorTests} contains tests for the {@link EnumValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class EnumValueGeneratorTests extends BaseTest {

   /**
    * Test for invoking {@link EnumValueGenerator#generateValue} with an {@link EnumValueGenerator} which was created with unique
    * selections.
    */
   @Test
   public void generateValueUniqueSelections() {
      final EnumValueGenerator<ExamplePojoEnum> enumValueGenerator =
         new EnumValueGenerator<>(ExamplePojoEnum.class, true);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Set<ExamplePojoEnum> usedValues = new HashSet<>();

         for (int i = 0; i < ExamplePojoEnum.values().length; i++) {
            final ExamplePojoEnum value = enumValueGenerator.generateValue();

            Assert.assertNotNull(value);
            Assert.assertTrue(usedValues.add(value));
         }
      }
   }

   /**
    * Test for invoking {@link EnumValueGenerator#generateValue} with an {@link EnumValueGenerator} which was created with non-unique
    * selections.
    */
   @Test
   public void generateValueNonUniqueSelections() {
      final EnumValueGenerator<ExamplePojoEnum> enumValueGenerator =
         new EnumValueGenerator<>(ExamplePojoEnum.class, false);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final ExamplePojoEnum value = enumValueGenerator.generateValue();
         Assert.assertNotNull(value);
      }
   }

   /**
    * Test for invoking {@link EnumValueGenerator#getValueType} with an {@link EnumValueGenerator} which was created to generate
    * {@link ExamplePojoEnum} values.
    */
   @Test
   public void getValueType() {
      final EnumValueGenerator<ExamplePojoEnum> enumValueGenerator =
         new EnumValueGenerator<>(ExamplePojoEnum.class, false);

      Assert.assertEquals(ExamplePojoEnum.class, enumValueGenerator.getValueType());
   }

   /**
    * Test for attempting to create an {@link EnumValueGenerator} with a null enum type.
    */
   @Test
   public void enumValueGeneratorNullEnumType() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'valueType' parameter cannot be null.");

      new EnumValueGenerator<>(null, false);
   }

   /**
    * Test for attempting to create an {@link EnumValueGenerator} with an enum type that does not have any values.
    */
   @Test
   public void enumValueGeneratorEmptyEnumType() {
      this.expectedException.expect(AssertionError.class);
      this.expectedException.expectMessage("The 'values' parameter cannot be empty.");

      new EnumValueGenerator<>(EmptyEnum.class, false);
   }

   /**
    * {@code EmptyEnum} is an enum type that does not contain any values.
    */
   private enum EmptyEnum {

   }

}

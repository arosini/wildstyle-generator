package ar.wildstyle.valuegenerator;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.wildstyle.test.BaseTest;

/**
 * {@code FirstNameValueGeneratorTests} contains tests for the {@link FirstNameValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class FirstNameValueGeneratorTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() throws Exception {
      final URL firstNamesUrl = FirstNameValueGenerator.class.getClassLoader().getResource(FirstNameValueGenerator.FIRST_NAMES_FILE);
      final File firstNamesFile = new File(firstNamesUrl.toURI());
      this.firstNames = new HashSet<>(Files.readAllLines(firstNamesFile.toPath()));
   }

   /**
    * Test for invoking {@link FirstNameValueGenerator#generateValue} with a {@link FirstNameValueGenerator} which was created with unique
    * selections.
    */
   @Test
   public void generateValueUniqueSelections() {
      final FirstNameValueGenerator firstNameValueGenerator = new FirstNameValueGenerator(true);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Set<String> usedValues = new HashSet<>();

         for (int i = 0; i < this.firstNames.size(); i++) {
            final String value = firstNameValueGenerator.generateValue();

            Assert.assertTrue(value.length() > 0);
            Assert.assertTrue(this.firstNames.contains(value));
            Assert.assertTrue(usedValues.add(value));
         }
      }
   }

   /**
    * Test for invoking {@link FirstNameValueGenerator#generateValue} with a {@link FirstNameValueGenerator} which was created with
    * non-unique selections.
    */
   @Test
   public void generateValueNonUniqueSelections() {
      final FirstNameValueGenerator firstNameValueGenerator = new FirstNameValueGenerator(false);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final String value = firstNameValueGenerator.generateValue();

         Assert.assertTrue(value.length() > 0);
         Assert.assertTrue(this.firstNames.contains(value));
      }
   }

   /**
    * Test for invoking {@link FirstNameValueGenerator#getValueType} with a {@link FirstNameValueGenerator}.
    */
   @Test
   public void getValueType() {
      final FirstNameValueGenerator firstNameValueGenerator = new FirstNameValueGenerator(true);
      Assert.assertEquals(String.class, firstNameValueGenerator.getValueType());
   }

   /**
    * The set of first names.
    */
   private Set<String> firstNames;

}

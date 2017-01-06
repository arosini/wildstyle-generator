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
 * {@code LastNameValueGeneratorTests} contains tests for the {@link LastNameValueGenerator} class.
 *
 * @author Adam Rosini
 */
public class LastNameValueGeneratorTests extends BaseTest {

   /**
    * Initializes shared test objects.
    */
   @Before
   public void initialize() throws Exception {
      final URL lastNamesUrl = LastNameValueGenerator.class.getClassLoader().getResource(LastNameValueGenerator.LAST_NAMES_FILE);
      final File lastNamesFile = new File(lastNamesUrl.toURI());
      this.lastNames = new HashSet<>(Files.readAllLines(lastNamesFile.toPath()));
   }

   /**
    * Test for invoking {@link LastNameValueGenerator#generateValue} with a {@link LastNameValueGenerator} which was created with unique
    * selections.
    */
   @Test
   public void generateValueUniqueSelections() {
      final LastNameValueGenerator lastNameValueGenerator = new LastNameValueGenerator(true);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final Set<String> usedValues = new HashSet<>();

         for (int i = 0; i < this.lastNames.size(); i++) {
            final String value = lastNameValueGenerator.generateValue();

            Assert.assertTrue(value.length() > 0);
            Assert.assertTrue(this.lastNames.contains(value));
            Assert.assertTrue(usedValues.add(value));
         }
      }
   }

   /**
    * Test for invoking {@link LastNameValueGenerator#generateValue} with a {@link LastNameValueGenerator} which was created with non-unique
    * selections.
    */
   @Test
   public void generateValueNonUniqueSelections() {
      final LastNameValueGenerator lastNameValueGenerator = new LastNameValueGenerator(false);

      for (int x = 0; x < BaseTest.RANDOM_GENERATION_DEFAULT_RUN_COUNT; x++) {
         final String value = lastNameValueGenerator.generateValue();

         Assert.assertTrue(value.length() > 0);
         Assert.assertTrue(this.lastNames.contains(value));
      }
   }

   /**
    * Test for invoking {@link LastNameValueGenerator#getValueType} with a {@link LastNameValueGenerator}.
    */
   @Test
   public void getValueType() {
      final LastNameValueGenerator lastNameValueGenerator = new LastNameValueGenerator(true);
      Assert.assertEquals(String.class, lastNameValueGenerator.getValueType());
   }

   /**
    * The set of last names.
    */
   private Set<String> lastNames;

}

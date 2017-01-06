package ar.wildstyle.valuegenerator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code FirstNameValueGenerator} is an implementation of {@link ValueGenerator} which generates {@link String} values which represent
 * first names.
 */
public class FirstNameValueGenerator extends SetBasedValueGenerator<String> {

   /**
    * The name of the file which contains the list of first names to generate.
    */
   public static final String FIRST_NAMES_FILE = "first-names.txt";

   /**
    * Creates a new {@code FirstNameValueGenerator}. If {@code uniqueSelections} is true, all first names will be generated an equal number
    * of times before any first name is generated again. For example, all first names will be generated once, then all first names will be
    * generated twice, and so on. If {@code uniqueSelections} is false, any first name may be generated at any time.
    */
   public FirstNameValueGenerator(boolean uniqueSelections) {
      this(String.class, FirstNameValueGenerator.getFirstNames(), uniqueSelections);
   }

   /**
    * Returns the set of first names from {@value #FIRST_NAMES_FILE}.
    *
    * @pre // Files.readAllLines(new File(FIRST_NAMES_FILE).toPath()) does not throw an IOException
    * @post return != null
    */
   private static Set<String> getFirstNames() {
      try {
         final URL firstNamesUrl = FirstNameValueGenerator.class.getClassLoader().getResource(FirstNameValueGenerator.FIRST_NAMES_FILE);
         final File firstNamesFile = new File(firstNamesUrl.toURI());
         final Set<String> firstNames = new HashSet<>(Files.readAllLines(firstNamesFile.toPath()));
         return firstNames;
      }
      catch (final IOException | URISyntaxException e) {
         throw new AssertionError("Could not read the first names file.", e);
      }
   }

   /**
    * Creates a new {@code FirstNameValueGenerator}. This constructor is only for internal use.
    *
    * @pre valueType != null
    * @pre !values.isEmpty()
    * @see SetBasedValueGenerator#SetBasedValueGenerator
    */
   private FirstNameValueGenerator(Class<String> valueType, Set<String> values, boolean uniqueSelections) {
      // Precondition(s) asserted by the call to "this(...)".
      super(valueType, values, uniqueSelections);
   }

}

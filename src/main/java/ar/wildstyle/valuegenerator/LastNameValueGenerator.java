package ar.wildstyle.valuegenerator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code LastNameValueGenerator} is an implementation of {@link ValueGenerator} which generates {@link String} values which represent last
 * names.
 */
public class LastNameValueGenerator extends SetBasedValueGenerator<String> {

   /**
    * The name of the file which contains the list of last names to generate.
    */
   public static final String LAST_NAMES_FILE = "last-names.txt";

   /**
    * Creates a new {@code LastNameValueGenerator}. If {@code uniqueSelections} is true, all last names will be generated an equal number of
    * times before any last name is generated again. For example, all last names will be generated once, then all last names will be
    * generated twice, and so on. If {@code uniqueSelections} is false, any last name may be generated at any time.
    */
   public LastNameValueGenerator(boolean uniqueSelections) {
      this(String.class, LastNameValueGenerator.getLastNames(), uniqueSelections);
   }

   /**
    * Returns the set of last names from {@value #LAST_NAMES_FILE}.
    *
    * @pre // Files.readAllLines(new File(LAST_NAMES_FILE).toPath()) does not throw an IOException
    * @post return != null
    */
   private static Set<String> getLastNames() {
      try {
         final URL lastNamesUrl = LastNameValueGenerator.class.getClassLoader().getResource(LastNameValueGenerator.LAST_NAMES_FILE);
         final File lastNamesFile = new File(lastNamesUrl.toURI());
         final Set<String> lastNames = new HashSet<>(Files.readAllLines(lastNamesFile.toPath()));
         return lastNames;
      }
      catch (final IOException | URISyntaxException e) {
         throw new AssertionError("Could not read the last names file.", e);
      }
   }

   /**
    * Creates a new {@code LastNameValueGenerator}. This constructor is only for internal use.
    *
    * @pre valueType != null
    * @pre !values.isEmpty()
    * @see SetBasedValueGenerator#SetBasedValueGenerator
    */
   private LastNameValueGenerator(Class<String> valueType, Set<String> values, boolean uniqueSelections) {
      // Precondition(s) asserted by the call to "this(...)".
      super(valueType, values, uniqueSelections);
   }

}

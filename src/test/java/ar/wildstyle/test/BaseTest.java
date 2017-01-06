package ar.wildstyle.test;

import ar.wildstyle.WildstyleGenerator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * {@code BaseTest} contains common configuration and data for tests. All tests should extend this class.
 *
 * @author Adam Rosini
 */
public class BaseTest {

   /**
    * The number of times that random generation tests should be run.
    */
   public static final int RANDOM_GENERATION_DEFAULT_RUN_COUNT = 1000;

   /**
    * Ensures that assertions are enabled so the tests which expect assertions errors will pass.
    */
   @BeforeClass
   public static void enableAssertions() {
      ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
   }

   /**
    * Clears the registry before each test.
    */
   @Before
   public void clearRegistry() throws Exception {
      WildstyleGenerator.clearRegistry();
   }

   /**
    * Used for asserting expected exceptions and messages.
    */
   @Rule
   public ExpectedException expectedException = ExpectedException.none();

}

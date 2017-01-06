package ar.wildstyle.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@code AlreadyMappedExceptionTests} contains tests for the {@link AlreadyMappedException} class.
 *
 * @author Adam Rosini
 */
public class AlreadyMappedExceptionTests {

   /**
    * Test for creating a valid {@link AlreadyMappedException}.
    */
   @Test
   public void alreadyMappedException() {
      final String errorMessage = "Error message.";
      final AlreadyMappedException e = new AlreadyMappedException(errorMessage);

      Assert.assertEquals(errorMessage, e.getMessage());
   }

}

package ar.wildstyle.exception;

import ar.wildstyle.FieldMapping;
import java.lang.reflect.Field;

/**
 * {@code AlreadyMappedException} is used to indicate that a {@linkplain Field field} has already been
 * {@linkplain FieldMapping#map mapped} in a given {@linkplain FieldMapping field mapping}.
 *
 * @author Adam Rosini
 */
public class AlreadyMappedException extends Exception {

  /**
   * Creates a {@code AlreadyMappedException} with the given message.
   *
   * @see Exception#Exception(String)
   */
  public AlreadyMappedException(String message) {
    super(message);
  }

  /**
   * The serialization version of this class.
   */
  private static final long serialVersionUID = 1L;

}

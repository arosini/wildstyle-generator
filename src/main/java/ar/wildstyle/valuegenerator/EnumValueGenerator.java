package ar.wildstyle.valuegenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code EnumValueGenerator} is an implementation of {@link ValueGenerator} which generates {@code Enum} values of type {@code E}.
 */
public class EnumValueGenerator<E extends Enum<E>> extends SetBasedValueGenerator<E> {

   /**
    * Generates a new {@code EnumValueGenerator} for the given enum type. If {@code uniqueSelections} is true, all enum values for
    * {@code enumType} will be generated an equal number of times before any enum value is generated again. For example, all enum values
    * will be generated once, then all enum values will be generated twice, and so on. If {@code uniqueSelections} is false, any enum value
    * in {@code enumType} may be generated at any time.
    *
    * @pre enumType != null
    * @pre enumType.getEnumConstants().length > 0
    *
    * @see SetBasedValueGenerator#SetBasedValueGenerator
    */
   public EnumValueGenerator(Class<E> enumType, boolean uniqueSelections) {
      // Precondition asserted by the call to "this(...)".
      this(enumType, enumType == null ? null : new HashSet<E>(Arrays.asList(enumType.getEnumConstants())), uniqueSelections);
   }

   /**
    * Creates a new {@code EnumValueGenerator}. This constructor is only for internal use.
    *
    * @pre valueType != null
    * @pre !values.isEmpty()
    *
    * @see SetBasedValueGenerator#SetBasedValueGenerator
    */
   private EnumValueGenerator(Class<E> valueType, Set<E> values, boolean uniqueSelections) {
      // Precondition(s) asserted by the call to "this(...)".
      super(valueType, values, uniqueSelections);
   }

}

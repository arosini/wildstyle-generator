package ar.wildstyle.valuegenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ListBasedValueGenerator} generates objects of type {@code T} by selecting them from a predefined list in order.
 */
public class ListBasedValueGenerator<T> implements ValueGenerator<T> {

   /**
    * Creates a new {@code ListBasedValueGenerator} that will generate values from the given {@code values} in order. If
    * {@code repeatSelections} is true, {@code values} will be repeated every time the end of {@code values} is reached. Otherwise, null
    * values will be generated once all values in {@code values} have been generated once.
    *
    * @pre valueType != null
    * @pre !values.isEmpty()
    */
   public ListBasedValueGenerator(Class<T> valueType, List<T> values, boolean repeatSelections) {
      assert valueType != null;
      assert !values.isEmpty();

      this.valueType = valueType;
      this.values = new ArrayList<>(values);
      this.repeatSelections = repeatSelections;
      this.usedValues = new ArrayList<>();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public T generateValue() {
      if (this.values.isEmpty()) {
         if (!this.repeatSelections) {
            return null;
         }

         this.values.addAll(this.usedValues);
      }

      final T value = this.values.remove(0);
      this.usedValues.add(value);

      return value;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<T> getValueType() {
      return this.valueType;
   }

   /**
    * The type of value generated by this object generator.
    *
    * @invariant valueType != null
    */
   private final Class<T> valueType;

   /**
    * The list of values associated with this object generator.
    *
    * @invariant !values.isEmpty();
    */
   private final List<T> values;

   /**
    * Indicates if values in {@code values} will be returned more than once.
    */
   private final boolean repeatSelections;

   /**
    * The list of used values associated with this object generator.
    *
    * @invariant usedValues != null
    */
   private final List<T> usedValues;

}

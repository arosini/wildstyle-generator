package ar.wildstyle;

import java.lang.reflect.Field;

import ar.wildstyle.util.ReflectionUtils;
import ar.wildstyle.valuegenerator.ValueGenerator;

/**
 * {@code FieldMappingEntry} represents an entry in a {@linkplain FieldMapping field mapping} between a {@linkplain Field field} and a
 * {@linkplain V value} or field and a {@linkplain ValueGenerator value generator}.
 *
 * @author Adam Rosini
 */
class FieldMappingEntry<V> {

   /**
    * Creates a new {@code FieldMappingEntry} between the given field and the given value generator.
    *
    * @pre ReflectionUtils.isCompatible(field, valueGenerator)
    */
   public FieldMappingEntry(Field field, ValueGenerator<V> valueGenerator) {
      assert ReflectionUtils.isCompatible(field, valueGenerator);

      this.field = field;
      this.value = null;
      this.valueGenerator = valueGenerator;
   }

   /**
    * Creates a new {@code FieldMappingEntry} between the given field and the given value.
    *
    * @pre ReflectionUtils.isCompatible(field, value);
    */
   public FieldMappingEntry(Field field, V value) {
      assert ReflectionUtils.isCompatible(field, value);

      this.field = field;
      this.value = value;
      this.valueGenerator = null;
   }

   /**
    * Returns the field associated with this field mapping entry.
    *
    * @post ReflectionUtils.isCompatible(return, this.getOrGenerateValue())
    */
   public Field getField() {
      return this.field;
   }

   /**
    * Returns a value for the field associated with this field mapping entry. If this field mapping entry was constructed with a value
    * generator, the value generator will be used to generate a new value. If this field mapping entry was constructed with a value, that
    * value will be returned.
    *
    * @post ReflectionUtils.isCompatible(this.getField(), return)
    */
   public V getOrGenerateValue() {
      return this.valueGenerator == null ? this.value : this.valueGenerator.generateValue();
   }

   /**
    * The field associated with this field mapping entry.
    *
    * @invariant ReflectionUtils.isCompatible(field, valueGenerator)
    */
   private final Field field;

   /**
    * The value associated with this field mapping entry.
    *
    * @invariant valueGenerator != null || ReflectionUtils.isCompatible(field, value)
    */
   private final V value;

   /**
    * The value generator associated with this field mapping entry.
    *
    * @invariant valueGenerator == null || ReflectionUtils.isCompatible(field, valueGenerator)
    */
   private final ValueGenerator<V> valueGenerator;

}
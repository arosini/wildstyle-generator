package ar.wildstyle;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.wildstyle.exception.AlreadyMappedException;
import ar.wildstyle.util.ReflectionUtils;
import ar.wildstyle.valuegenerator.ValueGenerator;

/**
 * {@code FieldMapping} specifies a mapping between a type's {@linkplain Field fields} and {@linkplain FieldMappingEntry field mapping
 * entries}.
 *
 * @author Adam Rosini
 */
public class FieldMapping<T> implements Iterable<FieldMappingEntry<?>> {

   /**
    * Merges the two field mappings together such that the entries in field mapping A take precedence over the entries in field mapping B.
    *
    * @pre fieldMappingA != null
    * @pre fieldMappingB != null
    */
   public static <A> FieldMapping<A> merge(FieldMapping<A> fieldMappingA, FieldMapping<? super A> fieldMappingB) {
      assert fieldMappingA != null : "The 'fieldMappingA' parameter cannot be null.";
      assert fieldMappingB != null : "The 'fieldMappingB' parameter cannot be null.";

      final FieldMapping<A> fieldMapping = new FieldMapping<A>(fieldMappingA.getType());
      fieldMapping.fieldMap.putAll(fieldMappingB.fieldMap);
      fieldMapping.fieldMap.putAll(fieldMappingA.fieldMap);

      return fieldMapping;
   }

   /**
    * Creates a new {@code FieldMapping} for the given class or type.
    *
    * @pre type != null
    */
   public FieldMapping(Class<T> type) {
      assert type != null : "The 'type' parameter cannot be null.";

      this.type = type;
      this.fieldMap = new HashMap<>();
   }

   /**
    * Maps a {@linkplain Field field} with the given field name to the given value generator.
    * <p>
    * The mapping is made between the first unmapped field encountered in the class hierarchy that matches the given field name and is
    * {@linkplain ReflectionUtils#isCompatible(Field, ValueGenerator) compatible} with the given value generator, starting from this field
    * mapping's {@linkplain #getType type} and progressing upwards in the class hierarchy.
    *
    * @pre !fieldName.isEmpty()
    * @pre valueGenerator != null
    * @pre // findUnmappedField(valueGenerator.getValueType(), fieldName) does not throw an exception
    */
   public <V> void map(String fieldName, ValueGenerator<V> valueGenerator) {
      assert fieldName != null : "The 'fieldName' parameter cannot be null.";
      assert !fieldName.isEmpty() : "The 'fieldName' parameter cannot be empty.";
      assert valueGenerator != null : "The 'valueGenerator' parameter cannot be null.";

      try {
         final Field field = this.findUnmappedField(valueGenerator.getValueType(), fieldName);
         this.fieldMap.put(field, new FieldMappingEntry<V>(field, valueGenerator));
      }
      catch (NoSuchFieldException | AlreadyMappedException e) {
         assert false : e.getMessage();
      }
   }

   /**
    * Maps a {@linkplain Field field} with the given field name to the given value.
    * <p>
    * The mapping is made between the first unmapped field encountered in the class hierarchy that matches the given field name and is
    * {@linkplain ReflectionUtils#isCompatible(Field, Object) compatible} with the given value, starting from this field mapping's
    * {@linkplain #getType type} and progressing upwards in the class hierarchy.
    *
    * @pre !fieldName.isEmpty()
    * @pre // findUnmappedField(valueGenerator.getValueType(), fieldName) does not throw an exception
    */
   public <V> void map(String fieldName, V value) {
      assert fieldName != null : "The 'fieldName' parameter cannot be null.";
      assert !fieldName.isEmpty() : "The 'fieldName' parameter cannot be empty.";

      try {
         final Field field = this.findUnmappedField(value == null ? null : value.getClass(), fieldName);
         this.fieldMap.put(field, new FieldMappingEntry<V>(field, value));
      }
      catch (NoSuchFieldException | AlreadyMappedException e) {
         assert false : e.getMessage();
      }
   }

   /**
    * Finds the first unmapped {@linkplain Field field} in the class hierarchy that matches the given field name and is
    * {@linkplain ReflectionUtils#isCompatible(Field, Class) compatible} with the given type, starting from this field mapping's
    * {@linkplain #getType type} and progressing {@linkplain Class#getSuperclass upwards} in the class hierarchy. A {@code null} type
    * parameter indicates that the field must be compatible with a {@code null} value.
    *
    * @throws NoSuchFieldException if no field with the given field name that is compatible with the given type exists in this field mapping
    *            type's hierarchy
    * @throws AlreadyMappedException if all fields with the given field name that are compatible with the given type have already been
    *            mapped in this field mapping
    *
    * @pre !fieldName.isEmpty()
    * @post ReflectionUtils#isCompatible(return, type)
    */
   public Field findUnmappedField(Class<?> type, String fieldName) throws NoSuchFieldException, AlreadyMappedException {
      assert fieldName != null : "The 'fieldName' parameter cannot be null.";
      assert !fieldName.isEmpty() : "The 'fieldName' parameter cannot be empty.";

      Field field = null;
      boolean alreadyMapped = false;
      Class<?> currentType = this.type;

      // Loop upwards in the class hierarchy until the field is found.
      while (currentType != Object.class) {
         try {
            // Check if the current type has a field with a matching name.
            field = currentType.getDeclaredField(fieldName);

            // If the field has already been mapped, continue to the next class in the hierarchy.
            if (this.fieldMap.containsKey(field)) {
               alreadyMapped = true;
               field = null;
               currentType = currentType.getSuperclass();
               continue;
            }

            // If the field is compatible with type, this field should be returned.
            if (ReflectionUtils.isCompatible(field, type)) {
               alreadyMapped = false;
               break;
            }
         }
         catch (final NoSuchFieldException e) {
         }

         // Check the next class in the hierarchy.
         field = null;
         currentType = currentType.getSuperclass();
      }

      // If the field was already mapped, throw an exception.
      if (alreadyMapped) {
         throw new AlreadyMappedException(String.format(
            "The '%s' field that is assignable from '%s' has already been mapped as many times as possible in this field mapping.",
            fieldName, type == null ? null : type.getName()));
      }

      // If the field was not found, throw an exception.
      if (field == null) {
         throw new NoSuchFieldException(String.format(
            "Could not find a field named '%s' that is assignable from '%s' for the '%s' class or any of its parents.",
            fieldName, type == null ? null : type.getName(), this.getType().getName()));
      }

      return field;
   }

   /**
    * {@inheritDoc}
    *
    * @post while(return.hasNext()) { return.next() != null }
    */
   @Override
   public Iterator<FieldMappingEntry<?>> iterator() {
      return this.fieldMap.values().iterator();
   }

   /**
    * Returns the class or type associated with this field mapping.
    *
    * @post return != null
    */
   public Class<T> getType() {
      return this.type;
   }

   /**
    * The class or type associated with this field mapping.
    *
    * @invariant type != null
    */
   private final Class<T> type;

   /**
    * The map of fields to field mapping entries associated with this field mapping.
    *
    * @invariant fieldMap.entrySet().stream().allMatch(e -> e.getKey().equals(e.getValue().getField()))
    */
   private final Map<Field, FieldMappingEntry<?>> fieldMap;

}

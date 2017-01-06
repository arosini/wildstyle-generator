package ar.wildstyle.util;

import java.beans.Expression;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ar.wildstyle.valuegenerator.ValueGenerator;

/**
 * Contains reflection utility methods.
 *
 * @author Adam Rosini
 */
public class ReflectionUtils {

   /**
    * Returns true if the field is compatible with the given value generator.
    *
    * @pre field != null
    * @pre valueGenerator != null
    */
   public static boolean isCompatible(Field field, ValueGenerator<?> valueGenerator) {
      assert field != null;
      assert valueGenerator != null;

      if (field.getType().isPrimitive() && valueGenerator.canGenerateNull()) {
         return false;
      }

      return ReflectionUtils.isCompatible(field, valueGenerator.getValueType());
   }

   /**
    * Returns true if the given field can be assigned to the given value.
    *
    * @pre field != null;
    * @pre value !=null
    */
   public static boolean isCompatible(Field field, Object value) {
      assert field != null;

      if (value == null) {
         if (field.getType().isPrimitive()) {
            return false;
         }
         else {
            return true;
         }
      }

      return ReflectionUtils.isCompatible(field, value.getClass());
   }

   /**
    * Returns true if the given field can be assigned to an instance of the given type. A {@code null} type indicates the field must be
    * assignable to {@code null}.
    *
    * @pre field != null;
    */
   public static boolean isCompatible(Field field, Class<?> type) {
      assert field != null;

      boolean compatible = false;
      final Class<?> fieldType = field.getType();

      if (fieldType.isPrimitive()) {
         if (type == null) {
            compatible = false;
         }

         else if (fieldType.equals(Boolean.TYPE)) {
            compatible = type.equals(Boolean.class);
         }

         else if (fieldType.equals(Byte.TYPE)) {
            compatible = type.equals(Byte.class);
         }

         else if (fieldType.equals(Character.TYPE)) {
            compatible = type.equals(Character.class);
         }

         else if (fieldType.equals(Double.TYPE)) {
            compatible = type.equals(Double.class);
         }

         else if (fieldType.equals(Float.TYPE)) {
            compatible = type.equals(Float.class);
         }

         else if (fieldType.equals(Integer.TYPE)) {
            compatible = type.equals(Integer.class);
         }

         else if (fieldType.equals(Long.TYPE)) {
            compatible = type.equals(Long.class);
         }

         else if (fieldType.equals(Short.TYPE)) {
            compatible = type.equals(Short.class);
         }
      }
      else {
         compatible = (type == null || fieldType.isAssignableFrom(type));
      }

      return compatible;
   }

   /**
    * Sets the given field on the given object to the given value.
    *
    * @pre field.getDeclaringClass().isAssignableFrom(object.getClass())
    * @pre ReflectionUtils.isCompatible(field, value)
    */
   public static void setField(Object object, Field field, Object value) {
      assert field.getDeclaringClass().isAssignableFrom(object.getClass());
      assert ReflectionUtils.isCompatible(field, value);

      try {
         field.setAccessible(true);
         field.set(object, value);
      }
      catch (final SecurityException | IllegalAccessException | IllegalArgumentException e) {
         throw new IllegalArgumentException(e.getMessage());
      }
   }

   /**
    * Creates a new instance of the given type using the given list of constructor arguments. If the given list of constructor arguments
    * contains a {@linkplain ValueGenerator value generator}, the value resulting from {@link ValueGenerator#generateValue} is used during
    * construction of the new instance.
    *
    * @throws IllegalArgumentException if there is an exception thrown when creating the new instance
    *
    * @pre type != null
    * @pre constructorArgsOrGenerator != null
    * @post return != null
    */
   public static <T> T newInstance(Class<T> type, List<?> constructorArgs) {
      assert type != null;
      assert constructorArgs != null;

      final List<Object> resolvedConstructorArgValues = new ArrayList<>(constructorArgs.size());

      for (final Object valueOrValueGenerator : constructorArgs) {
         if (valueOrValueGenerator instanceof ValueGenerator) {
            resolvedConstructorArgValues.add(((ValueGenerator<?>)valueOrValueGenerator).generateValue());
         }
         else {
            resolvedConstructorArgValues.add(valueOrValueGenerator);
         }
      }

      try {
         return type.cast(new Expression(type, "new", resolvedConstructorArgValues.toArray()).getValue());
      }
      catch (final Exception e) {
         throw new IllegalArgumentException(
            String.format("Could not construct an instance of '%s' using the resolved list of constructor arguments: %s", type,
               resolvedConstructorArgValues),
            e);
      }
   }

}

package ar.wildstyle;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import ar.wildstyle.util.ReflectionUtils;
import ar.wildstyle.valuegenerator.ValueGenerator;

/**
 * {@code ObjectGeneratorBuilder} is used to build and register {@link ObjectGenerator} instances.
 *
 * @see ObjectGenerator
 * @see FieldMapping
 */
class ObjectGeneratorBuilder<T> {

   /**
    * Creates a new {@code ObjectGeneratorBuilder} with the given type. {@link ObjectGenerator} instances {@linkplain #register registered}
    * by this object generator builder will {@linkplain ObjectGenerator#generateValue generate} values of the given type.
    *
    * @pre type != null
    */
   public ObjectGeneratorBuilder(Class<T> type) {
      assert type != null : "The 'type' parameter cannot be null.";

      this.name = ObjectGenerator.DEFAULT_NAME;
      this.fieldMapping = new FieldMapping<>(type);
      this.constructorArgs = ObjectGenerator.DEFAULT_CONSTRUCTOR_ARGS;
      this.nullChance = ObjectGenerator.DEFAULT_NULL_CHANCE;
      this.parent = null;
   }

   /**
    * Returns the type associated with this object generator builder.
    *
    * @post return != null
    */
   public Class<T> getType() {
      return this.fieldMapping.getType();
   }

   /**
    * Returns the field mapping associated with this object generator builder.
    *
    * @post return != null
    */
   public FieldMapping<T> getFieldMapping() {
      return this.fieldMapping;
   }

   /**
    * Adds a mapping between a {@linkplain Field field} with the given field name and the given value generator to this object generator
    * builder's {@linkplain #getFieldMapping field mapping}.
    * <p>
    * The mapping is made between the first unmapped field encountered in the class hierarchy that matches the given field name and is
    * {@linkplain ReflectionUtils#isCompatible(Field, ValueGenerator) compatible} with the given value generator, starting from this object
    * generator builder's {@linkplain #getType type} and progressing upwards in the class hierarchy.
    *
    * @pre !fieldName.isEmpty()
    * @pre valueGenerator != null
    * @pre // FieldMapping.findUnmappedField(valueGenerator.getValueType(), fieldName) does not throw an exception
    */
   public ObjectGeneratorBuilder<T> mapField(String fieldName, ValueGenerator<?> valueGenerator) {
      // Precondition(s) asserted by the call to "FieldMapping#map(...)".
      this.fieldMapping.map(fieldName, valueGenerator);
      return this;
   }

   /**
    * Adds a mapping between a {@linkplain Field field} with the given field name and the given value to this object generator builder's
    * {@linkplain #getFieldMapping field mapping}.
    * <p>
    * The mapping is made between the first unmapped field encountered in the class hierarchy that matches the given field name and is
    * {@linkplain ReflectionUtils#isCompatible(Field, Object) compatible} with the given value, starting from this object generator
    * builder's {@linkplain #getType type} and progressing upwards in the class hierarchy.
    *
    * @pre !fieldName.isEmpty()
    * @pre // FieldMapping.findUnmappedField(valueGenerator.getValueType(), fieldName) does not throw an exception
    */
   public ObjectGeneratorBuilder<T> mapField(String fieldName, Object value) {
      // Precondition(s) asserted by the call to "FieldMapping#map(...)".
      this.fieldMapping.map(fieldName, value);
      return this;
   }

   /**
    * Returns the name associated with this object generator builder.
    *
    * @post !return.isEmpty()
    */
   public String getName() {
      return this.name;
   }

   /**
    * Sets the name associated with this object generator builder. {@link ObjectGenerator} instances {@linkplain #register registered} by
    * this object generator builder will be associated with the given name.
    *
    * @pre !name.isEmpty()
    */
   public ObjectGeneratorBuilder<T> setName(String name) {
      assert name != null : "The 'name' parameter cannot be null.";
      assert !name.isEmpty() : "The 'name' parameter cannot be empty.";

      this.name = name;
      return this;
   }

   /**
    * Returns the null chance associated with this object generator builder.
    *
    * @post return >= 0
    * @post return <= 100
    */
   public double getNullChance() {
      return this.nullChance;
   }

   /**
    * Sets the null chance associated with this object generator builder. {@link ObjectGenerator} instances {@linkplain #register
    * registered} by this object generator builder will be associated with the given null chance.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public ObjectGeneratorBuilder<T> setNullChance(double nullChance) {
      assert nullChance >= 0 : "The 'nullChance' parameter must be greater than or equal to 0.";
      assert nullChance <= 100 : "The 'nullChance' paramter must be less than or equal to 100.";

      this.nullChance = nullChance;
      return this;
   }

   /**
    * Returns the constructor arguments associated with this object generator builder.
    *
    * @post return != null
    */
   public List<Object> getConstructorArgs() {
      return this.constructorArgs;
   }

   /**
    * Sets the constructor arguments associated with this object generator builder. {@link ObjectGenerator} instances {@linkplain #register
    * registered} by this object generator builder will be associated with the given constructor arguments. Note the given list of
    * constructor arguments may contain {@linkplain ValueGenerator value generators}, in which case the value generators
    * {@linkplain ValueGenerator#generateValue generated} values are used when {@linkplain ObjectGenerator#generateValue generating}
    * objects.
    *
    * @pre constructorArgs != null
    */
   public ObjectGeneratorBuilder<T> setConstructorArgs(Object... constructorArgs) {
      assert constructorArgs != null : "The 'constructorArgs' parameter cannot be null.";

      this.constructorArgs = Arrays.asList(constructorArgs);
      return this;
   }

   /**
    * Returns the parent object generator associated with this object generator builder.
    */
   public ObjectGenerator<? super T> getParent() {
      return this.parent;
   }

   /**
    * Sets the parent object generator associated with this object generator builder.
    */
   public ObjectGeneratorBuilder<T> setParent(ObjectGenerator<? super T> parent) {
      this.parent = parent;
      return this;
   }

   /**
    * Sets the parent object generator associated with this object generator builder to the registered object generator for the given value
    * type and name.
    */
   public ObjectGeneratorBuilder<T> setParent(Class<? super T> valueType, String name) {
      this.parent = WildstyleGenerator.getObjectGenerator(valueType, name);
      return this;
   }

   /**
    * Sets the parent object generator associated with this object generator builder to the registered object generator for the given value
    * type.
    */
   public ObjectGeneratorBuilder<T> setParent(Class<? super T> valueType) {
      this.parent = WildstyleGenerator.getObjectGenerator(valueType);
      return this;
   }

   /**
    * Builds, registers and returns a new {@link ObjectGenerator} based on the current state of this object generator builder.
    *
    * @pre // ReflectionUtils.newInstance(getType(), getConstructorArgs()) does not throw an exception
    * @post return.getValueType().equals(getType())
    * @post return.getName().equals(getName())
    * @post WildstyleGenerator.getObjectGenerator(return.getValueType(), return.getName()) != null
    */
   public ObjectGenerator<T> register() {
      // Precondition(s) asserted by the call to "new ObjectGenerator<T>(...)".
      final ObjectGenerator<T> objectGenerator =
         new ObjectGenerator<T>(this.name, this.fieldMapping, this.constructorArgs, this.nullChance, this.parent);

      WildstyleGenerator.registerObjectGenerator(objectGenerator);
      return objectGenerator;
   }

   /**
    * The name associated with this object generator builder.
    *
    * @invariant !name.isEmpty()
    */
   private String name;

   /**
    * The field mapping associated with this object generator builder.
    *
    * @invariant fieldMapping != null
    */
   private final FieldMapping<T> fieldMapping;

   /**
    * The list of constructor arguments associated with this object generator builder.
    *
    * @invariant constructorArgs != null
    */
   private List<Object> constructorArgs;

   /**
    * The null chance associated with this object generator builder.
    *
    * @invariant nullChance >= 0
    * @invariant nullChance <= 100
    */
   private double nullChance;

   /**
    * The parent object generator associated with this object generator builder.
    */
   private ObjectGenerator<? super T> parent;

}

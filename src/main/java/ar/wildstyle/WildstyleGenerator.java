package ar.wildstyle;

import ar.wildstyle.valuegenerator.ValueGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code WildstyleGenerator} provides utilities to assist with the generation of object instances. It acts as a
 * registry for {@link ObjectGenerator} instances, which are registered according to their
 * {@linkplain ObjectGenerator#getValueType value type} and {@linkplain ObjectGenerator#getName name}. If an object
 * generator does not have a name, it is registered with the {@linkplain ObjectGenerator#DEFAULT_NAME default object
 * generator name}
 * <p>
 * Once an object generator has been registered, {@linkplain WildstyleGenerator#generate(Class, String)} can be used to
 * invoke the object generator's {@linkplain ObjectGenerator#generateValue generate} method. If the object generator was
 * registered with the default object generator name, it can be invoked by simply passing the value type to the
 * {@linkplain WildstyleGenerator#generate(Class) generate} method.
 * <p>
 * Object generators may be registered by first creating an {@link ObjectGeneratorBuilder}, configuring the object
 * generator as desired and then calling {@link ObjectGeneratorBuilder#register}.
 *
 * @see ObjectGenerator
 * @see ObjectGeneratorBuilder
 * @see FieldMapping
 * @see ValueGenerator
 *
 * @author Adam Rosini
 */
public class WildstyleGenerator {

  /**
   * Creates a new {@code ObjectGeneratorBuilder}, which can {@linkplain ObjectGeneratorBuilder#register register} new
   * {@link ObjectGenerator} instances which generate objects of the given type.
   *
   * @pre type != null
   */
  public static <T> ObjectGeneratorBuilder<T> createObjectGenerator(Class<T> type) {
    // Precondition(s) asserted by the call to "new ObjectGeneratorBuilder<T>(...)".
    return new ObjectGeneratorBuilder<T>(type);
  }

  /**
   * Returns the object generator that has been registered with the given value type and the
   * {@linkplain ObjectGenerator#DEFAULT_NAME default object generator name}, or null if none exists.
   *
   * @post return == null || (return.getValueType().equals(valueType) && return.getName().equals(DEFAULT_NAME))
   */
  public static <T> ObjectGenerator<T> getObjectGenerator(Class<T> valueType) {
    return WildstyleGenerator.getObjectGenerator(valueType, ObjectGenerator.DEFAULT_NAME);
  }

  /**
   * Returns the object generator that has been registered with the given value type and name, or null if none exists.
   *
   * @post return == null || (return.getValueType().equals(valueType) && return.getName().equals(name))
   *
   */
  @SuppressWarnings("unchecked")
  public static <T> ObjectGenerator<T> getObjectGenerator(Class<T> valueType, String name) {
    final Map<String, ObjectGenerator<?>> valueTypeGenerators = WildstyleGenerator.objectGenerators.get(valueType);
    if (valueTypeGenerators == null) {
      return null;
    }

    // TODO: Remove warning suppression.
    return (ObjectGenerator<T>) valueTypeGenerators.get(name);
  }

  /**
   * Generates an object using the object generator registered with the the {@linkplain ObjectGenerator#DEFAULT_NAME
   * default object generator name} that produces the given value type.
   *
   * @pre valueType != null
   * @pre getObjectGenerator(valueType, DEFAULT_NAME) != null
   */
  public static <T> T generate(Class<T> valueType) {
    // Precondition(s) asserted by the call to "WildstyleGenerator.generate(...)".
    return WildstyleGenerator.generate(valueType, ObjectGenerator.DEFAULT_NAME);
  }

  /**
   * Generates an object using the object generator registered with the given object generator name that produces the
   * given value type.
   *
   * @pre valueType != null
   * @pre !objectGeneratorName.isEmpty()
   * @pre getObjectGenerator(valueType, objectGeneratorName) != null
   */
  public static <T> T generate(Class<T> valueType, String objectGeneratorName) {
    assert valueType != null : "The 'valueType' parameter cannot be null.";
    assert objectGeneratorName != null : "The 'objectGeneratorName' parameter cannot be null.";
    assert !objectGeneratorName.isEmpty() : "The 'objectGeneratorName' parameter cannot be empty.";

    final ObjectGenerator<?> objectGenerator = WildstyleGenerator.getObjectGenerator(valueType, objectGeneratorName);

    assert objectGenerator != null : String.format(
        "Did not find a '%s' value generator with the name '%s'.", valueType, objectGeneratorName);

    return valueType.cast(objectGenerator.generateValue());
  }

  /**
   * Removes all registered {@link ObjectGenerator} instances from the registry.
   */
  public static void clearRegistry() {
    WildstyleGenerator.objectGenerators.clear();
  }

  /**
   * Register the given object generator. If an object generator with the same {@linkplain ObjectGenerator#getName name}
   * and {@linkplain ObjectGenerator#getValueType value type} already exists, it is overwritten.
   *
   * @pre objectGenerator != null
   * @post getObjectGenerator(objectGenerator.getValueType(), objectGenerator.getName()) != null
   */
  protected static void registerObjectGenerator(ObjectGenerator<?> objectGenerator) {
    assert objectGenerator != null : "The 'objectGenerator' parameter cannot be null.";

    if (!WildstyleGenerator.objectGenerators.containsKey(objectGenerator.getValueType())) {
      WildstyleGenerator.objectGenerators.put(objectGenerator.getValueType(),
          new HashMap<String, ObjectGenerator<?>>());
    }

    WildstyleGenerator.objectGenerators.get(objectGenerator.getValueType()).put(objectGenerator.getName(),
        objectGenerator);
  }

  /**
   * The map of classes to object generator maps.
   *
   * @invariant objectGenerators != null
   */
  private static final Map<Class<?>, Map<String, ObjectGenerator<?>>> objectGenerators = new HashMap<>();

}

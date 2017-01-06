package ar.wildstyle.valuegenerator;

import java.math.BigDecimal;
import java.util.Random;

/**
 * {@code FloatValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Float} values.
 *
 * @author Adam Rosini
 */
public class FloatValueGenerator implements ValueGenerator<Float> {

   /**
    * The default minimum value of an {@link Float} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final float DEFAULT_MIN = -Float.MAX_VALUE;

   /**
    * The default maximum value of an {@link Float} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final float DEFAULT_MAX = Float.MAX_VALUE;

   /**
    * The default percent chance that {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final float DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code FloatValueGenerator} that will {@linkplain #generateValue generate} {@link Float} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive).
    */
   public FloatValueGenerator() {
      this(FloatValueGenerator.DEFAULT_MIN, FloatValueGenerator.DEFAULT_MAX,
         FloatValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code FloatValueGenerator} that will {@linkplain #generateValue generate} {@link Float} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive).
    *
    * @pre min <= max
    */
   public FloatValueGenerator(float min, float max) {
      // Precondition(s) asserted by the call to "this(...)".
      this(min, max, FloatValueGenerator.DEFAULT_NULL_CHANCE);
   }


   /**
    * Creates a new {@code FloatValueGenerator} that will {@linkplain #generateValue generate} {@link Float} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public FloatValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(FloatValueGenerator.DEFAULT_MIN, FloatValueGenerator.DEFAULT_MAX, nullChance);
   }

   /**
    * Creates a new {@code FloatValueGenerator} that will {@linkplain #generateValue generate} {@link Float} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre min <= max
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public FloatValueGenerator(float min, float max, double nullChance) {
      assert min <= max : "The 'min' parameter must be less than or equal to the 'max' parameter.";
      assert nullChance >= 0 : "The 'nullChance' parameter must be greater than or equal to 0.";
      assert nullChance <= 100 : "The 'nullChance' parameter must be less than or equal to 100.";

      this.min = BigDecimal.valueOf(min);
      this.max = BigDecimal.valueOf(max);
      this.nullChance = nullChance;
      this.random = new Random();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Float generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      final BigDecimal range = this.max.subtract(this.min);
      final BigDecimal randomValueInRange = BigDecimal.valueOf(this.random.nextFloat()).multiply(range);

      return randomValueInRange.add(this.min).floatValue();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Float> getValueType() {
      return Float.class;
   }

   /**
    * The minimum possible value (inclusive) of a {@link Float} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant min.compareTo(max) <= 0
    */
   private final BigDecimal min;

   /**
    * The maximum possible value (inclusive) of a {@link Float} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant max.compareTo(min) >= 0
    */
   private final BigDecimal max;

   /**
    * The percent chance that a {@code null} value is {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant nullChance >= 0
    * @invariant nullChance <= 100
    */
   private final double nullChance;

   /**
    * The random instance associated with this value generator.
    *
    * @invariant random != null
    */
   private final Random random;

}

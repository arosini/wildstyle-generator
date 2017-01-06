package ar.wildstyle.valuegenerator;

import java.math.BigDecimal;
import java.util.Random;

/**
 * {@code DoubleValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Double} values.
 *
 * @author Adam Rosini
 */
public class DoubleValueGenerator implements ValueGenerator<Double> {

   /**
    * The default minimum value of an {@link Double} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_MIN = -Double.MAX_VALUE;

   /**
    * The default maximum value of an {@link Double} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_MAX = Double.MAX_VALUE;

   /**
    * The default percent chance that {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code DoubleValueGenerator} that will {@linkplain #generateValue generate} {@link Double} instances with a value
    * between {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive).
    */
   public DoubleValueGenerator() {
      this(DoubleValueGenerator.DEFAULT_MIN, DoubleValueGenerator.DEFAULT_MAX,
         DoubleValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code DoubleValueGenerator} that will {@linkplain #generateValue generate} {@link Double} instances with a value
    * between the given {@code min} and the given {@code max} (inclusive).
    *
    * @pre min <= max
    */
   public DoubleValueGenerator(double min, double max) {
      // Precondition(s) asserted by the call to "this(...)".
      this(min, max, DoubleValueGenerator.DEFAULT_NULL_CHANCE);
   }


   /**
    * Creates a new {@code DoubleValueGenerator} that will {@linkplain #generateValue generate} {@link Double} instances with a value
    * between {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public DoubleValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(DoubleValueGenerator.DEFAULT_MIN, DoubleValueGenerator.DEFAULT_MAX, nullChance);
   }

   /**
    * Creates a new {@code DoubleValueGenerator} that will {@linkplain #generateValue generate} {@link Double} instances with a value
    * between the given {@code min} and the given {@code max} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre min <= max
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public DoubleValueGenerator(double min, double max, double nullChance) {
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
   public Double generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      final BigDecimal range = this.max.subtract(this.min);
      final BigDecimal randomValueInRange = BigDecimal.valueOf(this.random.nextDouble()).multiply(range);

      return randomValueInRange.add(this.min).doubleValue();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Double> getValueType() {
      return Double.class;
   }

   /**
    * The minimum possible value (inclusive) of a {@link Double} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant min.compareTo(max) <= 0
    */
   private final BigDecimal min;

   /**
    * The maximum possible value (inclusive) of a {@link Double} {@linkplain #generateValue generated} by this value generator.
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

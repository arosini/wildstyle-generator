package ar.wildstyle.valuegenerator;

import java.math.BigInteger;
import java.util.Random;

/**
 * {@code IntegerValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Integer} values.
 *
 * @author Adam Rosini
 */
public class IntegerValueGenerator implements ValueGenerator<Integer> {

   /**
    * The default minimum value of an {@link Integer} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final int DEFAULT_MIN = Integer.MIN_VALUE;

   /**
    * The default maximum value of an {@link Integer} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final int DEFAULT_MAX = Integer.MAX_VALUE;

   /**
    * The default percent chance that {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code IntegerValueGenerator} that will {@linkplain #generateValue generate} {@link Integer} instances with a value
    * between {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive).
    */
   public IntegerValueGenerator() {
      this(IntegerValueGenerator.DEFAULT_MIN, IntegerValueGenerator.DEFAULT_MAX,
         IntegerValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code IntegerValueGenerator} that will {@linkplain #generateValue generate} {@link Integer} instances with a value
    * between the given {@code min} and the given {@code max} (inclusive).
    *
    * @pre min <= max
    */
   public IntegerValueGenerator(int min, int max) {
      // Precondition(s) asserted by the call to "this(...)".
      this(min, max, IntegerValueGenerator.DEFAULT_NULL_CHANCE);
   }


   /**
    * Creates a new {@code IntegerValueGenerator} that will {@linkplain #generateValue generate} {@link Integer} instances with a value
    * between {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public IntegerValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(IntegerValueGenerator.DEFAULT_MIN, IntegerValueGenerator.DEFAULT_MAX, nullChance);
   }

   /**
    * Creates a new {@code IntegerValueGenerator} that will {@linkplain #generateValue generate} {@link Integer} instances with a value
    * between the given {@code min} and the given {@code max} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre min <= max
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public IntegerValueGenerator(int min, int max, double nullChance) {
      assert min <= max : "The 'min' parameter must be less than or equal to the 'max' parameter.";
      assert nullChance >= 0 : "The 'nullChance' parameter must be greater than or equal to 0.";
      assert nullChance <= 100 : "The 'nullChance' parameter must be less than or equal to 100.";

      this.min = BigInteger.valueOf(min);
      this.max = BigInteger.valueOf(max);
      this.nullChance = nullChance;
      this.random = new Random();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Integer generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      final BigInteger range = this.max.subtract(this.min);

      BigInteger randomValueInRange;
      do {
         randomValueInRange = new BigInteger(range.bitLength(), this.random);
      } while (randomValueInRange.compareTo(range) > 0);

      return randomValueInRange.add(this.min).intValue();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Integer> getValueType() {
      return Integer.class;
   }

   /**
    * The minimum possible value (inclusive) of an {@link Integer} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant min.compareTo(max) <= 0
    */
   private final BigInteger min;

   /**
    * The maximum possible value (inclusive) of an {@link Integer} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant max.compareTo(min) >= 0
    */
   private final BigInteger max;

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

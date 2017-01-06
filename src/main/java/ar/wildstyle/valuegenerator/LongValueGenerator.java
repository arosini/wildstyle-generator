package ar.wildstyle.valuegenerator;

import java.math.BigInteger;
import java.util.Random;

/**
 * {@code LongValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Long} values.
 *
 * @author Adam Rosini
 */
public class LongValueGenerator implements ValueGenerator<Long> {

   /**
    * The default minimum value of an {@link Long} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final long DEFAULT_MIN = Long.MIN_VALUE;

   /**
    * The default maximum value of an {@link Long} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final long DEFAULT_MAX = Long.MAX_VALUE;

   /**
    * The default percent chance that {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code LongValueGenerator} that will {@linkplain #generateValue generate} {@link Long} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive).
    */
   public LongValueGenerator() {
      this(LongValueGenerator.DEFAULT_MIN, LongValueGenerator.DEFAULT_MAX,
         LongValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code LongValueGenerator} that will {@linkplain #generateValue generate} {@link Long} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive).
    *
    * @pre min <= max
    */
   public LongValueGenerator(long min, long max) {
      // Precondition(s) asserted by the call to "this(...)".
      this(min, max, LongValueGenerator.DEFAULT_NULL_CHANCE);
   }


   /**
    * Creates a new {@code LongValueGenerator} that will {@linkplain #generateValue generate} {@link Long} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public LongValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(LongValueGenerator.DEFAULT_MIN, LongValueGenerator.DEFAULT_MAX, nullChance);
   }

   /**
    * Creates a new {@code LongValueGenerator} that will {@linkplain #generateValue generate} {@link Long} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre min <= max
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public LongValueGenerator(long min, long max, double nullChance) {
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
   public Long generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      final BigInteger range = this.max.subtract(this.min);

      BigInteger randomValueInRange;
      do {
         randomValueInRange = new BigInteger(range.bitLength(), this.random);
      } while (randomValueInRange.compareTo(range) > 0);

      return randomValueInRange.add(this.min).longValue();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Long> getValueType() {
      return Long.class;
   }

   /**
    * The minimum possible value (inclusive) of a {@link Long} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant min.compareTo(max) <= 0
    */
   private final BigInteger min;

   /**
    * The maximum possible value (inclusive) of a {@link Long} {@linkplain #generateValue generated} by this value generator.
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

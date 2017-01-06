package ar.wildstyle.valuegenerator;

import java.math.BigInteger;
import java.util.Random;

/**
 * {@code ShortValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Short} values.
 *
 * @author Adam Rosini
 */
public class ShortValueGenerator implements ValueGenerator<Short> {

   /**
    * The default minimum value of an {@link Short} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final short DEFAULT_MIN = Short.MIN_VALUE;

   /**
    * The default maximum value of an {@link Short} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final short DEFAULT_MAX = Short.MAX_VALUE;

   /**
    * The default percent chance that {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code ShortValueGenerator} that will {@linkplain #generateValue generate} {@link Short} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive).
    */
   public ShortValueGenerator() {
      this(ShortValueGenerator.DEFAULT_MIN, ShortValueGenerator.DEFAULT_MAX,
         ShortValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code ShortValueGenerator} that will {@linkplain #generateValue generate} {@link Short} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive).
    *
    * @pre min <= max
    */
   public ShortValueGenerator(short min, short max) {
      // Precondition(s) asserted by the call to "this(...)".
      this(min, max, ShortValueGenerator.DEFAULT_NULL_CHANCE);
   }


   /**
    * Creates a new {@code ShortValueGenerator} that will {@linkplain #generateValue generate} {@link Short} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public ShortValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(ShortValueGenerator.DEFAULT_MIN, ShortValueGenerator.DEFAULT_MAX, nullChance);
   }

   /**
    * Creates a new {@code ShortValueGenerator} that will {@linkplain #generateValue generate} {@link Short} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre min <= max
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public ShortValueGenerator(short min, short max, double nullChance) {
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
   public Short generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      final BigInteger range = this.max.subtract(this.min);

      BigInteger randomValueInRange;
      do {
         randomValueInRange = new BigInteger(range.bitLength(), this.random);
      } while (randomValueInRange.compareTo(range) > 0);

      return randomValueInRange.add(this.min).shortValue();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Short> getValueType() {
      return Short.class;
   }

   /**
    * The minimum possible value (inclusive) of a {@link Short} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant min.compareTo(max) <= 0
    */
   private final BigInteger min;

   /**
    * The maximum possible value (inclusive) of a {@link Short} {@linkplain #generateValue generated} by this value generator.
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

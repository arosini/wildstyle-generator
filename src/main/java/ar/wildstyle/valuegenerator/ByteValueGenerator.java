package ar.wildstyle.valuegenerator;

import java.math.BigInteger;
import java.util.Random;

/**
 * {@code ByteValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Byte} values.
 *
 * @author Adam Rosini
 */
public class ByteValueGenerator implements ValueGenerator<Byte> {

   /**
    * The default minimum value of an {@link Byte} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final byte DEFAULT_MIN = Byte.MIN_VALUE;

   /**
    * The default maximum value of an {@link Byte} {@linkplain #generateValue generated} by instances of this class.
    */
   public static final byte DEFAULT_MAX = Byte.MAX_VALUE;

   /**
    * The default percent chance that {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code ByteValueGenerator} that will {@linkplain #generateValue generate} {@link Byte} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive).
    */
   public ByteValueGenerator() {
      this(ByteValueGenerator.DEFAULT_MIN, ByteValueGenerator.DEFAULT_MAX,
         ByteValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code ByteValueGenerator} that will {@linkplain #generateValue generate} {@link Byte} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive).
    *
    * @pre min <= max
    */
   public ByteValueGenerator(byte min, byte max) {
      // Precondition(s) asserted by the call to "this(...)".
      this(min, max, ByteValueGenerator.DEFAULT_NULL_CHANCE);
   }


   /**
    * Creates a new {@code ByteValueGenerator} that will {@linkplain #generateValue generate} {@link Byte} instances with a value between
    * {@value #DEFAULT_MIN} and {@value #DEFAULT_MAX} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public ByteValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(ByteValueGenerator.DEFAULT_MIN, ByteValueGenerator.DEFAULT_MAX, nullChance);
   }

   /**
    * Creates a new {@code ByteValueGenerator} that will {@linkplain #generateValue generate} {@link Byte} instances with a value between
    * the given {@code min} and the given {@code max} (inclusive) with a {@code nullChance} percent chance of being null.
    *
    * @pre min <= max
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public ByteValueGenerator(byte min, byte max, double nullChance) {
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
   public Byte generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      final BigInteger range = this.max.subtract(this.min);

      BigInteger randomValueInRange;
      do {
         randomValueInRange = new BigInteger(range.bitLength(), this.random);
      } while (randomValueInRange.compareTo(range) > 0);

      return randomValueInRange.add(this.min).byteValue();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Byte> getValueType() {
      return Byte.class;
   }

   /**
    * The minimum possible value (inclusive) of a {@link Byte} {@linkplain #generateValue generated} by this value generator.
    *
    * @invariant min.compareTo(max) <= 0
    */
   private final BigInteger min;

   /**
    * The maximum possible value (inclusive) of a {@link Byte} {@linkplain #generateValue generated} by this value generator.
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

package ar.wildstyle.valuegenerator;

import java.util.Random;

/**
 * {@code BooleanValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Boolean} values.
 *
 * @author Adam Rosini
 */
public class BooleanValueGenerator implements ValueGenerator<Boolean> {

   /**
    * The default percent chance that a {@code true} {@link Boolean} will be {@linkplain #generateValue generated} by instances of this
    * class.
    */
   public static final double DEFAULT_TRUE_CHANCE = 50;

   /**
    * The default percent chance that a {@code null} value will be {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code BooleanValueGenerator} that will {@linkplain #generateValue generate} {@link Boolean} instances with a
    * {@value #DEFAULT_TRUE_CHANCE} percent chance of being {@code true}.
    */
   public BooleanValueGenerator() {
      this(BooleanValueGenerator.DEFAULT_TRUE_CHANCE, BooleanValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code BooleanValueGenerator} that will {@linkplain #generateValue generate} {@link Boolean} instances with a
    * {@code trueChance} percent chance of being {@code true}.
    *
    * @pre trueChance >= 0
    * @pre trueChance <= 100
    */
   public BooleanValueGenerator(double trueChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(trueChance, BooleanValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code BooleanValueGenerator} that will {@linkplain #generateValue generate} {@link Boolean} instances that have a
    * {@code nullChance} percent chance of being null and {@code trueChance} percent chance of being true if they are not null.
    *
    * @pre trueChance >= 0
    * @pre trueChance <= 100
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public BooleanValueGenerator(double trueChance, double nullChance) {
      assert trueChance >= 0 : "The 'trueChance' parameter must be greater than or equal to 0.";
      assert trueChance <= 100 : "The 'trueChance' parameter must be less than or equal to 100.";
      assert nullChance >= 0 : "The 'nullChance' parameter must be greater than or equal to 0.";
      assert nullChance <= 100 : "The 'nullChance' parameter must be less than or equal to 100.";

      this.trueChance = trueChance;
      this.nullChance = nullChance;
      this.random = new Random();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Boolean generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      return this.random.nextInt(100) < this.trueChance;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Boolean> getValueType() {
      return Boolean.class;
   }

   /**
    * The percent chance that a {@code true} value is {@linkplain #generateValue generated} by this value generator, if the generated value
    * is not {@code null}.
    */
   private final double trueChance;

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

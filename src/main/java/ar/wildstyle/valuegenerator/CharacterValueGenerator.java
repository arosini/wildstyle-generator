package ar.wildstyle.valuegenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * {@code CharacterValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Character} values.
 *
 * @author Adam Rosini
 */
public class CharacterValueGenerator implements ValueGenerator<Character> {

   /**
    * The default list of values generated by instances of this class.
    */
   public static final List<Character> DEFAULT_ALLOWABLE_CHARACTERS = new ArrayList<>(95);
   static {
      for (int i = 32; i < 127; i++) {
         CharacterValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS.add((char)i);
      }
   }

   /**
    * The default percent chance that a {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code CharacterValueGenerator} that will generate {@link Character} values found in the
    * {@linkplain #DEFAULT_ALLOWABLE_CHARACTERS default list of allowable characters}.
    */
   public CharacterValueGenerator() {
      this(CharacterValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS, CharacterValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code CharacterValueGenerator} that will generate {@link Character} values found in {@code allowableCharacters}.
    *
    * @pre !allowableCharacters.isEmpty()
    */
   public CharacterValueGenerator(List<Character> allowableCharacters) {
      // Precondition(s) asserted by the call to "this(...)".
      this(allowableCharacters, CharacterValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code CharacterValueGenerator} that will generate {@link Character} values found in the
    * {@linkplain #DEFAULT_ALLOWABLE_CHARACTERS default list of allowable characters}, with a {@code nullChance} percent chance of
    * generating a {@code null} value.
    *
    * @pre nullChance >= 0
    * @pre nullChance <=100
    */
   public CharacterValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(CharacterValueGenerator.DEFAULT_ALLOWABLE_CHARACTERS, nullChance);
   }


   /**
    * Creates a new {@code CharacterValueGenerator} that will generate {@link Character} values found in {@code allowableCharacters}, with a
    * {@code nullChance} percent chance of generating a {@code null} value.
    *
    * @pre !allowableCharacters.isEmpty()
    * @pre nullChance >= 0
    * @pre nullChance <=100
    */
   public CharacterValueGenerator(List<Character> allowableCharacters, double nullChance) {
      assert allowableCharacters != null : "The 'allowableCharacters' parameter cannot be null.";
      assert !allowableCharacters.isEmpty() : "The 'allowableCharacters' parameter cannot be empty.";
      assert nullChance >= 0 : "The 'nullChance' parameter must be greater than or equal to 0.";
      assert nullChance <= 100 : "The 'nullChance' parameter must be less than or equal to 100.";

      this.allowableCharacters = allowableCharacters;
      this.nullChance = nullChance;
      this.random = new Random();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Character generateValue() {
      if (this.random.nextInt(100) < this.nullChance) {
         return null;
      }

      return this.allowableCharacters.get(this.random.nextInt(this.allowableCharacters.size()));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Character> getValueType() {
      return Character.class;
   }

   /**
    * The list of characters that a value {@linkplain #generateValue generated} by this value generator will be chosen from.
    *
    * @invariant allowableCharacters != null
    */
   private final List<Character> allowableCharacters;

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

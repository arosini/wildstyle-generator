package ar.wildstyle.valuegenerator;

import java.util.Date;

/**
 * {@code DateValueGenerator} is an implementation of {@link ValueGenerator} that generates {@link Date} values.
 */
public class DateValueGenerator implements ValueGenerator<Date> {

   /**
    * The default minimum Epoch time of a {@link Date} {@linkplain #generateValue generated} by instances of this class. Corresponds to the
    * year 1970.
    */
   public static final long DEFAULT_MIN_EPOCH_TIME = 0;

   /**
    * The default maximum Epoch time of a {@link Date} {@linkplain #generateValue generated} by instances of this class. Corresponds to the
    * year 2040.
    */
   public static final long DEFAULT_MAX_EPOCH_TIME = 2208988800L;

   /**
    * The default percent chance that {@code null} value is {@linkplain #generateValue generated} by instances of this class.
    */
   public static final double DEFAULT_NULL_CHANCE = 0;

   /**
    * Creates a new {@code DateValueGenerator} that will generate {@link Date} values with an Epoch time between
    * {@value #DEFAULT_MIN_EPOCH_TIME} and {@value #DEFAULT_MAX_EPOCH_TIME}.
    */
   public DateValueGenerator() {
      this(DateValueGenerator.DEFAULT_MIN_EPOCH_TIME, DateValueGenerator.DEFAULT_MAX_EPOCH_TIME, DateValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code DateValueGenerator} that will generate {@link Date} values with an Epoch time between the given minimum Epoch
    * time and the given maximum Epoch time.
    *
    * @pre minEpochTime <= maxEpochTime
    */
   public DateValueGenerator(long minEpochTime, long maxEpochTime) {
      // Precondition(s) asserted by the call to "this(...)".
      this(minEpochTime, maxEpochTime, DateValueGenerator.DEFAULT_NULL_CHANCE);
   }

   /**
    * Creates a new {@code DateValueGenerator} that will generate {@link Date} values with an Epoch time between
    * {@value #DEFAULT_MIN_EPOCH_TIME} and {@value #DEFAULT_MAX_EPOCH_TIME}, with a {@code nullChance} percent chance of being null.
    *
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public DateValueGenerator(double nullChance) {
      // Precondition(s) asserted by the call to "this(...)".
      this(DateValueGenerator.DEFAULT_MIN_EPOCH_TIME, DateValueGenerator.DEFAULT_MAX_EPOCH_TIME, nullChance);
   }

   /**
    * Creates a new {@code DateValueGenerator} that will generate {@link Date} values with an Epoch time between the given minimum Epoch
    * time and the given maximum Epoch time, with a {@code nullChance} percent chance of being null.
    *
    * @pre minEpochTime <= maxEpochTime
    * @pre nullChance >= 0
    * @pre nullChance <= 100
    */
   public DateValueGenerator(long minEpochTime, long maxEpochTime, double nullChance) {
      assert minEpochTime <= maxEpochTime : "The 'minEpochTime' parameter must be less than or equal to the 'maxEpochTime' parameter.";

      // Precondition(s) asserted by the call to "new LongValueGenerator(...)".
      this.longValueGenerator = new LongValueGenerator(minEpochTime, maxEpochTime, nullChance);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Date generateValue() {
      final Long time = this.longValueGenerator.generateValue();
      return time == null ? null : new Date(time);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Class<Date> getValueType() {
      return Date.class;
   }

   /**
    * The long value generator associated with this date value generator.
    */
   private final LongValueGenerator longValueGenerator;

}

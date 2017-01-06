package ar.wildstyle.test;

/**
 * {@code ExamplePojo} is an example POJO that is used for testing.
 *
 * @author Adam Rosini
 */
public class ExamplePojo extends ExamplePojoParent {

   /**
    * The name of the example {@code private Boolean}.
    */
   public static final String EXAMPLE_PRIVATE_BOOLEAN_WRAPPER_FIELD_NAME = "examplePrivateBooleanWrapper";

   /**
    * The name of the example {@code private boolean}.
    */
   public static final String EXAMPLE_PRIVATE_BOOLEAN_FIELD_NAME = "examplePrivateBoolean";

   /**
    * The name of the example {@code private Byte}.
    */
   public static final String EXAMPLE_PRIVATE_BYTE_WRAPPER_FIELD_NAME = "examplePrivateByteWrapper";

   /**
    * The name of the example {@code private byte}.
    */
   public static final String EXAMPLE_PRIVATE_BYTE_FIELD_NAME = "examplePrivateByte";

   /**
    * The name of the example {@code private Character}.
    */
   public static final String EXAMPLE_PRIVATE_CHARACTER_FIELD_NAME = "examplePrivateCharacter";

   /**
    * The name of the example {@code private char}.
    */
   public static final String EXAMPLE_PRIVATE_CHAR_FIELD_NAME = "examplePrivateChar";

   /**
    * The name of the example {@code private Double}.
    */
   public static final String EXAMPLE_PRIVATE_DOUBLE_WRAPPER_FIELD_NAME = "examplePrivateDoubleWrapper";

   /**
    * The name of the example {@code private double}.
    */
   public static final String EXAMPLE_PRIVATE_DOUBLE_FIELD_NAME = "examplePrivateDouble";

   /**
    * The name of the example {@code private enum}.
    */
   public static final String EXAMPLE_PRIVATE_ENUM_FIELD_NAME = "examplePrivateEnum";

   /**
    * The name of the example {@code private Float}.
    */
   public static final String EXAMPLE_PRIVATE_FLOAT_WRAPPER_FIELD_NAME = "examplePrivateFloatWrapper";

   /**
    * The name of the example {@code private float}.
    */
   public static final String EXAMPLE_PRIVATE_FLOAT_FIELD_NAME = "examplePrivateFloat";

   /**
    * The name of the example {@code private Integer}.
    */
   public static final String EXAMPLE_PRIVATE_INTEGER_FIELD_NAME = "examplePrivateInteger";

   /**
    * The name of the example {@code private int}.
    */
   public static final String EXAMPLE_PRIVATE_INT_FIELD_NAME = "examplePrivateInt";

   /**
    * The name of the example {@code private Long}.
    */
   public static final String EXAMPLE_PRIVATE_LONG_WRAPPER_FIELD_NAME = "examplePrivateLongWrapper";

   /**
    * The name of the example {@code private long}.
    */
   public static final String EXAMPLE_PRIVATE_LONG_FIELD_NAME = "examplePrivateLong";

   /**
    * The name of the example {@code private Object}.
    */
   public static final String EXAMPLE_PRIVATE_OBJECT_FIELD_NAME = "examplePrivateObject";

   /**
    * The name of the example {@code private Short}.
    */
   public static final String EXAMPLE_PRIVATE_SHORT_WRAPPER_FIELD_NAME = "examplePrivateShortWrapper";

   /**
    * The name of the example {@code private short}.
    */
   public static final String EXAMPLE_PRIVATE_SHORT_FIELD_NAME = "examplePrivateShort";

   /**
    * The name of the example {@code private String}.
    */
   public static final String EXAMPLE_PRIVATE_STRING_FIELD_NAME = "examplePrivateString";

   /**
    * The name of the example {@code private String} for a first name.
    */
   public static final String EXAMPLE_PRIVATE_FIRST_NAME_STRING_FIELD_NAME = "examplePrivateFirstNameString";

   /**
    * The name of the example {@code private String} for a last name.
    */
   public static final String EXAMPLE_PRIVATE_LAST_NAME_STRING_FIELD_NAME = "examplePrivateLastNameString";

   /**
    * The name of the example duplicate {@code private String}.
    */
   public static final String EXAMPLE_DUPLICATE_PRIVATE_STRING_FIELD_NAME = "exampleDuplicatePrivateString";

   /**
    * The name of the example {@code private String} without a getter.
    */
   public static final String EXAMPLE_PRIVATE_STRING_FIELD_WITHOUT_GETTER_NAME = "examplePrivateStringFieldWithoutGetter";

   /**
    * The name of the example {@code private String} that should always be null.
    */
   public static final String EXAMPLE_PRIVATE_STRING_FIELD_ALWAYS_NULL = "examplePrivateStringFieldAlwaysNull";

   /**
    * An example enumeration.
    */
   public enum ExamplePojoEnum {

      /**
       * The first example enumeration value.
       */
      ENUM_VAL_1,

      /**
       * The second example enumeration value.
       */
      ENUM_VAL_2

   }

   /**
    * Creates a new {@code ExamplePojo}.
    */
   public ExamplePojo() {
   }

   /**
    * Creates a new {@code ExamplePojo} with the given example {@code private String}.
    */
   public ExamplePojo(String examplePrivateString) {
      this.examplePrivateString = examplePrivateString;
   }

   /**
    * Creates a new {@code ExamplePojo} with the given {@code examplePojoParent}.
    */
   public ExamplePojo(ExamplePojoParent examplePojoParent) {
      this.examplePrivateObject = examplePojoParent;
   }

   /**
    * Creates a new {@code ExamplePojo} with the given parameters.
    */
   public ExamplePojo(ExamplePojo examplePojo, int examplePrivateInt) {
      this.examplePrivateObject = examplePojo;
      this.examplePrivateInt = examplePrivateInt;
   }

   /**
    * Creates a new {@code ExamplePojo} with the given parameters.
    */
   public ExamplePojo(ExamplePojo examplePojo, Integer examplePrivateInteger) {
      this.examplePrivateObject = examplePojo;
      this.examplePrivateInteger = examplePrivateInteger;
   }

   /**
    * Creates a new {@code ExamplePojo} with the given parameters.
    */
   public ExamplePojo(ExamplePojo examplePojo, Integer examplePrivateInteger, int examplePrivateInt) {
      this.examplePrivateObject = examplePojo;
      this.examplePrivateInteger = examplePrivateInteger;
      this.examplePrivateInt = examplePrivateInt;
   }

   /**
    * Returns the example {@code private Boolean}.
    */
   public Boolean getExamplePrivateBooleanWrapper() {
      return this.examplePrivateBooleanWrapper;
   }

   /**
    * Returns the example {@code private boolean}.
    */
   public boolean getExamplePrivateBoolean() {
      return this.examplePrivateBoolean;
   }

   /**
    * Returns the example {@code private Byte}.
    */
   public Byte getExamplePrivateByteWrapper() {
      return this.examplePrivateByteWrapper;
   }

   /**
    * Returns the example {@code private byte}.
    */
   public byte getExamplePrivateByte() {
      return this.examplePrivateByte;
   }

   /**
    * Returns the example {@code private Character}.
    */
   public Character getExamplePrivateCharacter() {
      return this.examplePrivateCharacter;
   }

   /**
    * Returns the example {@code private char}.
    */
   public char getExamplePrivateChar() {
      return this.examplePrivateChar;
   }

   /**
    * Returns the example {@code private Double}.
    */
   public Double getExamplePrivateDoubleWrapper() {
      return this.examplePrivateDoubleWrapper;
   }

   /**
    * Returns the example {@code private enum}.
    */
   public ExamplePojoEnum getExamplePrivateEnum() {
      return this.examplePrivateEnum;
   }

   /**
    * Returns the example {@code private long}.
    */
   public double getExamplePrivateDouble() {
      return this.examplePrivateDouble;
   }

   /**
    * Returns the example {@code private Float}.
    */
   public Float getExamplePrivateFloatWrapper() {
      return this.examplePrivateFloatWrapper;
   }

   /**
    * Returns the example {@code private float}.
    */
   public float getExamplePrivateFloat() {
      return this.examplePrivateFloat;
   }

   /**
    * Returns the example {@code private Integer}.
    */
   public Integer getExamplePrivateInteger() {
      return this.examplePrivateInteger;
   }

   /**
    * Returns the example {@code private int}.
    */
   public int getExamplePrivateInt() {
      return this.examplePrivateInt;
   }

   /**
    * Returns the example {@code private Long}.
    */
   public Long getExamplePrivateLongWrapper() {
      return this.examplePrivateLongWrapper;
   }

   /**
    * Returns the example {@code private long}.
    */
   public long getExamplePrivateLong() {
      return this.examplePrivateLong;
   }

   /**
    * Returns the example {@code private Object}.
    */
   public Object getExamplePrivateObject() {
      return this.examplePrivateObject;
   }

   /**
    * Returns the example {@code private Short}.
    */
   public Short getExamplePrivateShortWrapper() {
      return this.examplePrivateShortWrapper;
   }

   /**
    * Returns the example {@code private short}.
    */
   public short getExamplePrivateShort() {
      return this.examplePrivateShort;
   }

   /**
    * Returns the example {@code private String}.
    */
   public String getExamplePrivateString() {
      return this.examplePrivateString;
   }

   /**
    * Returns the example {@code private String} for a first name.
    */
   public String getExamplePrivateFirstNameString() {
      return this.examplePrivateFirstNameString;
   }

   /**
    * Returns the example {@code private String} for a last name.
    */
   public String getExamplePrivateLastNameString() {
      return this.examplePrivateLastNameString;
   }

   /**
    * Returns the example duplicate {@code private String} from the child.
    */
   public String getExampleDuplicatePrivateStringChild() {
      return this.exampleDuplicatePrivateString;
   }

   /**
    * Returns the example {@code private String} that is always null.
    */
   public String getExamplePrivateStringFieldAlwaysNull() {
      return this.examplePrivateStringFieldAlwaysNull;
   }

   /**
    * An example {@code private Boolean}.
    */
   private Boolean examplePrivateBooleanWrapper;

   /**
    * An example {@code private boolean}.
    */
   private boolean examplePrivateBoolean;

   /**
    * An example {@code private Byte}.
    */
   private Byte examplePrivateByteWrapper;

   /**
    * An example {@code private byte}.
    */
   private byte examplePrivateByte;

   /**
    * An example {@code private Character}.
    */
   private Character examplePrivateCharacter;

   /**
    * An example {@code private char}.
    */
   private char examplePrivateChar;

   /**
    * An example {@code private Double}.
    */
   private Double examplePrivateDoubleWrapper;

   /**
    * An example {@code private double}.
    */
   private double examplePrivateDouble;

   /**
    * An example {@code private enum}.
    */
   private ExamplePojoEnum examplePrivateEnum;

   /**
    * An example {@code private Float}.
    */
   private Float examplePrivateFloatWrapper;

   /**
    * An example {@code private float}.
    */
   private float examplePrivateFloat;

   /**
    * An example {@code private Integer}.
    */
   private Integer examplePrivateInteger;

   /**
    * An example {@code private int}.
    */
   private int examplePrivateInt;

   /**
    * An example {@code private Long}.
    */
   private Long examplePrivateLongWrapper;

   /**
    * An example {@code private long}.
    */
   private long examplePrivateLong;

   /**
    * An example {@code private Object}.
    */
   private Object examplePrivateObject;

   /**
    * An example {@code private Short}.
    */
   private Short examplePrivateShortWrapper;

   /**
    * An example {@code private short}.
    */
   private short examplePrivateShort;

   /**
    * An example {@code private String}.
    */
   private String examplePrivateString;

   /**
    * An example {@code private String} for a first name.
    */
   private String examplePrivateFirstNameString;

   /**
    * An example {@code private String} for a first name.
    */
   private String examplePrivateLastNameString;

   /**
    * An example {@code private String} that also exists in the {@linkplain ExamplePojoParent parent} class.
    */
   private String exampleDuplicatePrivateString;

   /**
    * An example {@code private String} that does not have a getter.
    */
   @SuppressWarnings("unused")
   private String examplePrivateStringFieldWithoutGetter;

   /**
    * An example {@code private String} that is always null.
    */
   private String examplePrivateStringFieldAlwaysNull;

}

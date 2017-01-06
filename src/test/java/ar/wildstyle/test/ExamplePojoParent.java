package ar.wildstyle.test;

/**
 * {@code ExamplePojoParent} is an example of a parent POJO that is used for testing.
 *
 * @author Adam Rosini
 */
public class ExamplePojoParent {

   /**
    * The name of the example parent {@code private String}.
    */
   public static final String EXAMPLE_PARENT_PRIVATE_STRING_FIELD_NAME =
      "exampleParentPrivateString";

   /**
    * Returns the example parent private {@code String}.
    */
   public String getExampleParentPrivateString() {
      return this.exampleParentPrivateString;
   }

   /**
    * Returns the example duplicate {@code private String} from the parent.
    */
   public String getExampleDuplicatePrivateStringParent() {
      return this.exampleDuplicatePrivateString;
   }

   /**
    * An example {@code private String}.
    */
   private String exampleParentPrivateString;

   /**
    * An example {@code private String} that also exists in the {@linkplain ExamplePojo child} class.
    */
   private String exampleDuplicatePrivateString;

}

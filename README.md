# Wildstyle Generator

Wildstyle Generator provides utilities for generating objects in Java. It was primarily built to help generate POJOs, that could have a wide variety of data, to be used in tests.

In order to use this library, you must enable assertions by either starting the JVM with the `-ea` flag or by calling `ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);` before the class which uses the library is loaded.

# Installation

## Maven

```
<dependency>
    <groupId>com.github.arosini</groupId>
    <artifactId>wildstyle-generator</artifactId>
    <version>0.1</version>
</dependency>
```

## Gradle

```
compile 'com.github.arosini:wildstyle-generator:0.1'
```

# Basic Usage

Imagine the following `Employee` class:

```java
public class Employee {

  public enum EmployeeType {
    FULL_TIME,
    PART_TIME
  }
  
  public Employee() {}
  
  public Employee(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  private String firstName;
  private String lastName;
  private int yearsEmployed;
  private double hourlyWage;
  private EmployeeType employeeType;
  
  // Getters, setters, etc...

}
```

In order to create `Employee` objects for testing, you might find yourself utility methods or calling a constructor / setters whenever it is necessary.
However, `WildstyleGenerator` allows you to minimize the code you need to write by performing the following:

1. Registering an `ObjectGenerator` for the `Employee` class:

  ```java
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .mapField("firstName", new FirstNameValueGenerator(true))
    .mapField("lastName", new LastNameValueGenerator(true))
    .mapField("yearsEmployed", 10)
    .mapField("hourlyWage", new DoubleValueGenerator(10.0, 100.0))
    .mapField("employeeType", new EnumValueGenerator(EmployeeType.class, true)) 
    .register();
  ```

2. Generating an `Employee` object using the `ObjectGenerator` registered in step 1:

  ```java
  Employee employee = WildstyleGenerator.generate(Employee.class);
  ```
  
  The generated `Employee` object will have a random first/last name, a `yearsEmployed` of 10, an `hourlyWage` between 10 and 100 (inclusive) and a randomly assigned `EmployeeType`. As shown, your objects can be a combination of hard-coded values and value generators. See [the value generators](#value-generators) section for more details on value generators.
 
# Object Generator Features

The object generators you register can have any of the following features:

* Field mappings, as shown in the [basic usage](#basic-usage) section. For example, the following will register an object generator who will generate `Employee` instances with a random first name and the last name "Smith".

  ```java
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .mapField("firstName", new FirstNameValueGenerator(true))
    .mapField("lastName", "Smith")
    .register();
  ```

  As shown, the value can be either a hard-coded value, or a value generator. If a field is mapped to a value generator, whenever the object generator generates an instance, the value generator will generate a new value for the field it is mapped to. Field mappings are made to the first unmapped field in the class hierarchy that is compatible with either the value or value generator provided, starting wtih the defined object class and working up.
  
  For more information on this, read the contracts on the `FieldMapping` class.

* A name, in case you want multiple object generators for the same class. For example, you might have the following two `Employee` object generators:

  ```java
  // Register the "fullTime" Employee object generator, which only generates full time employees.
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .setName("fullTime")
    .mapField("employeeType", EmployeeType.FULL_TIME) 
    .register();
    
    // Register the "partTime" Employee object generator, which only generates part time employees.
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .setName("partTime")
    .mapField("employeeType", EmployeeType.PART_TIME) 
    .register();
  ```
  
  When you want to get `Employee` objects with the object generators created above, you need to give the name of the object generator:
 
  ```java
  Employee fullTimeEmployee = WildstyleGenerator.generate("fullTime", Employee.class);
  Employee partTimeEmployee = WildstyleGenerator.generate("partTime", Employee.class);
  ```
  
  If you register two object generators that produce the same object type with the same name, the first one you registered will be overwritten by the second. If you don't give an object generator a name, it will be given the name "default".

* A chance of generating a null object. The following object generator has a 50% chance of generating null:

  ```java
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .setNullChance(50.0) 
    .register();
  ```

* A list of constructor arguments (or constructor argument value generators). The following object generator will be generate `Employee` objects using a constructor which accepts two `String` values (the second is randomly generated each time an object is generated):

  ```java
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .setConstructorArgs("firstArgValue", new StringValueGenerator()) 
    .register();
  ```
  
  Note that you will not be able to register an object generator if the list of constructor arguments does not correspond to a constructor for the type the object generator will generate. By default, constructor arguments are empty so if the type being generated does not have a default constructor, the constructor arguments must be set to valid values.

* A parent object generator to inherit from:

  ```java
  // Register the default Employee object generator.
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .mapField("firstName", new FirstNameValueGenerator(true))
    .mapField("lastName", new LastNameValueGenerator(true))
    .mapField("yearsEmployed", 10)
    .mapField("hourlyWage", new DoubleValueGenerator(10.0, 100.0))
    .mapField("employeeType", new EnumValueGenerator(EmployeeType.class, true)) 
    .register();
    
  // Register the "fullTime" Employee object generator, which only generates full time employees.
  // It inherits from the "default" Employee object generator created above.
  WildstyleGenerator.createObjectGenerator(Employee.class)
    .setName("fullTime")
    .mapField("employeeType", EmployeeType.FULL_TIME)
    .setParent(WildstyleGenerator.getObjectGenerator(Employee.class))
    // or you can just do ".setParent(Employee.class)" or ".setParent(Employee.class, "default")
    .register();
  ```
  
   The parent must generate objects of the same type, or of a superclass. Any duplicate field mappings that exist in the child and parent are overwritten by the child.

# Value Generators
The `ar.wildstyle.valuegenerator` package contains value generators for all primitive / primitive wrapper types, as well as some other value generators:

* FirstNameValueGenerator
* LastNameValueGenerator
* ListBasedValueGenerator
* SetBasedValueGenerator
* EnumValueGenerator
* DateValueGenerator

For the specifics on the usage of any `ValueGenerator` implementation, read the contracts on its class and available constructors.

# Coding Style

This project is coded using DBC (Design by Contract). See https://en.wikipedia.org/wiki/Design_by_contract. You can see examples of how it is implemented by reading the code. An error in a contract is considered a bug, same as code. If you find a contract is incorrect, confusing or can be improved in any way, please create an issue.

# Contribution

Before starting development, make sure you can build the project with `./gradlew clean build`. The project is built with Java 8.

If you would like to contribute, you should do the following:

1) Create an issue for the feature/bug and wait until the issue is approved.

2) Create a feature branch off of the current development branch, which will have the same name as the next release version (such as 1.0).

3) Write any new contracts involved and get them approved. This step may be skipped if no new contracts are necessary. Throw an UnsupportedOperationException when an implementation is required.

4) Implement the contracts, and write unit tests for the code. Once the code, contracts and tests have been reviewed and approved, it will be merged and included in the next release.

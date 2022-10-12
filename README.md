# spring-rest-advanced

Creating a Customized Annotation for the Annotation @Qualifier:
 - Create a ENUM Class and insert whatever you want (for example 'URGENT' and 'NORMAL')
 - Create an ANNOTATION Class
   - Annotate this class with:
     - @Retention(RetentionPolicy.Runtime)
     - @Qualifier
   - In the ANNOTATION Class insert the ENUM'S CLASS NAME + value();
 - Do not forget to insert the customized annotation in the classes that you created

To substitute external properties by command line: 
 - On Windows: set SERVER_PORT=8082
 - On MAC: export SERVER_PORT=8082

Modifying a Profile via terminal:
 - set SPRING_PROFILES_ACTIVE=production
   - Use 'export' instead of 'set' for linux and Mac

Using JPA
 - the 'spring.jpa.generate-ddl=true' says that the table will be created automatically as soon as you run the code
 - the 'spring.jpa.hibernate.ddl-auto=create' says that the tables will be dropped and created automatically everytime that the code runs
 - the 'spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect' aids us to create a foreign key

Using EntityManager
- By using it, we are able to save, consult data in our database

Using Flyway
- To repair a file by CMD, use the file 'flyway.yml' and on cmd type: 
./mvnw flyway:repair -Dflyway.configFiles=src/main/resources/flyway.yml

PLUGINS Used:
    - Spring Assistant: Auto completion of the configuration properties in your yaml
    - Lombok: @Data, which contains @Getter, @Setter, @EqualsAndHashCode and @ToString.
    - Spring-boot-starter-validation: 
        -- @Valid: Annotation in the controller layer that tells that before calling the method 'add' for example, it will be made a
            validation of the instance restaurant inside the controller
        -- @Validated: Similar to the annotation @Valid, but this one accepts arguments, for example: (Groups.RestaurantRecord.class)
        -- @NotNull: This one ensures that the field with this annotation always gets data
        -- @DecimalMin("0"): It says the minimum allowed for the property
        -- @PositiveOrZero: As the name implies, the value must be "0" or greater than "0"
        -- @NotEmpty: It says the value must not be empty.
        -- @NotBlank: It says the field cannot be blank. For example: "    "

Errors and Fixing Errors
- 2022-08-31 22:11:28.547  WARN 16160 --- [nio-8080-exec-1] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Ignored field "address" (class com.developer.beverageapi.domain.model.Restaurant) encountered; mapper configured not to allow this; nested exception is com.fasterxml.jackson.databind.exc.IgnoredPropertyException: Ignored field "address" (class com.developer.beverageapi.domain.model.Restaurant) encountered; mapper configured not to allow this (5 known properties: "id", "delivery", "payments", "name", "kitchen"])<EOL> at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 4, column: 21] (through reference chain: com.developer.beverageapi.domain.model.Restaurant["address"])]
- 2022-08-31 22:14:16.976  WARN 16160 --- [nio-8080-exec-4] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Unrecognized field "description" (class com.developer.beverageapi.domain.model.Restaurant), not marked as ignorable; nested exception is com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "description" (class com.developer.beverageapi.domain.model.Restaurant), not marked as ignorable (5 known properties: "id", "delivery", "payments", "name", "kitchen"])<EOL> at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 4, column: 25] (through reference chain: com.developer.beverageapi.domain.model.Restaurant["description"])]
- 2022-09-01 11:20:28.835  WARN 12060 --- [nio-8080-exec-4] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: "SDC"]
- The instance BindingResult stores the constraints violations
- To solve the error: Caused by: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'group group0_ where group0_.id=1' at line 1
  - Just include in the class Group: @Table(name = "`group`"), it was like: @Table(name = "group") and 'group' is a reserved word in SQL
- Caused by: javax.validation.ConstraintViolationException: Validation failed for classes [com.developer.beverageapi.domain.model.Restaurant] during update time for groups [javax.validation.groups.Default, ]
  List of constraint violations:[
  ConstraintViolationImpl{interpolatedMessage='Mandatory description invalid', propertyPath=, rootBeanClass=class com.developer.beverageapi.domain.model.Restaurant, messageTemplate='Mandatory description invalid'}
  ]
  - THIS ERROR WAS CAUSED BECAUSE IN THE afterMigrate.SQL IT HAD THE FOLLOWING LINE:
    - insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, active, `open`) values (3, 'O_Matuto', 0, 2, utc_timestamp, utc_timestamp, true, true);
    IT WAS CHANGED TO:
    - insert into restaurant (id, name, delivery, kitchen_id, record_date, updated_date, active, `open`) values (3, 'O_Matuto', 15, 2, utc_timestamp, utc_timestamp, true, true);
  - As you can see, the problem is in the delivery field, it will be analysed latter in the RESTAURANT class if the annotation (@ZeroValueIncludesDescription(fieldValue = "delivery",
    fieldDescription = "name", mandatoryField = "Free Delivery")) has something related to it
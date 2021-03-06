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

PLUGINS Used:
    - Spring Assistant: Auto completion of the configuration properties in your yaml
    - Lomnbok: @Data, which contains @Getter, @Setter, @EqualsAndHashCode and @ToString.
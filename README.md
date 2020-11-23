
# Phalanx 
####  A Spartan CRUD Framework

Phalanx is a Spring boot based framework to reduce the code writing and speed up the development in a secure way to generate standardized Rest API with the use of inheritance and Entities configuration. 

The idea was taken from Grails scaffold principle with the difference that you won't have to write commands on the terminal for each model you create, neither you'll have to install any code generation tool on your computer nor generate a bunch of scaffold files that you will have to maintain after generated. With only adding the proper Annotations to your Controller and Service component classes you will get a simple and clean but full Rest API CRUD implementation for any entities you want.

Context in which the framework works by now:

  - Spring web (spring-boot-starter-web)
  - Spring data JPA (spring-boot-starter-data-jpa)

### Using the framework in a nutshell

Besides adding the maven dependency to the `pom.xml` file of your project you must start from creating the `@Entity` class for your model, second you create the `@Repository` class as you usually do for your Entity, note that it must extends/implements from a `PagingAndSortingRepository` interface; create the `@Service` class and add it the `@FullService` annotation with the dao attribute referencing to the `@Entity` class you've created before and finally extend it from the `FullService` class. The super class will force you to declare a constructor in which you will have to specify the `@Repository` class you have created before. Take note that it is very important to add the `@Autowired` annotation to this constructor so that Spring core can inject it to the main context. And finally create your `@Controller` class and extend it from `FullController` class. The super class will force you to declare a constructor in which you will have to specify the `@Service` class you have created before, don't forget to add the `@Autowired` annotation with this. And Woala! That will be all for having a full CRUD Rest API for your model.

### Step by step use

1. Create the Entity:
```java
@Entity
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  private String name;
  
  public Long getId() { return this.id; }
  public void setId(Long id) { this.id = id; }
  
  public String getName() { return this.name; }
  public void setName(String name) { this.name = name; }
}
```
I believe that this step is the most important one where you set up any validations, specifications and details about your model to ensure that your business needs are covered.

2. Create the Repository:
```java
@Repository  
public interface PersonRepository  extends PagingAndSortingRepository<Person, Serializable> {  
    @Query("Select p From Person p Where p.id = :id")  
    List<Person> findAllById(@Param("id") Long id);  
}
```

3. Create the Service:
```java
@Service  
@FullService(dao = Person.class)  
public class PersonService extends BaseService {  
  @Autowired  
  public PersonService(PersonRepository repository) {  
        super(repository);  
  }  
}
```

4. Create the Controller:
```java
@CrossOrigin  
@RestController  
@RequestMapping("/person")  
@EnableWebMvc
@Controller  
public class PersonController extends BaseController {  
  @Autowired  
  public PersonService(PersonService service) {  
        super(service);  
  }  
}
```

This text you see here is *actually* written in Markdown! To get a feel for Markdown's syntax, type some text into the left window and watch the results in the right.

### Installation


### Todos

 - The `DaoSetup` configuration file cannot find the dao attribute when you are working with `@Transactional` annotated classes, the workaround is to assign it directly in the Service class constructor:
 ```java
  @Autowired  
public PersonService(PersonRepository repository) {  
    super(repository);  
    //TODO Fix setup for being transactional service  
    super.entity = this.getClass().getAnnotation(BaseService.class).dao();  
}
```

License
----

MIT


**Free Software, Hell Yeah!**
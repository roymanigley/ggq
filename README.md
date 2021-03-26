# `GGQ` - go go Quarkus
> As part of the study at the HFTM Grenchen in the module XML processing, an example project was developed which is to illuminate specific XML technologies.   
>   
> **Chosen technology:**
> - XSLT
> - xStream as an alternative to JAXB 

## What is `GGQ`

> GGQ is a code generator for Quarkus which was implemented as a CLI. The input is an XML file which contains the entities (see example below).  
>  
> The transformation of the individual entities into the respective output files is implemented using XSL templates. 

The following types can be generated: 

- `JPA_REST` → REST Application incl. Swagger using EntityManager (100% test coverage)
- `HIBERNATE_PANACHE_REST` → REST Application with pagination incl. Swagger using PanacheRepository (100% test coverage)
- `MONGO_PANACHE_REST` → REST Application incl. Swagger using ReactiveMongoPanacheRepository (~80% test coverage)
- `MONGO_PANACHE_REST_OIDC` → REST Application with OIDC (KeyCloak) incl. Swagger using ReactiveMongoPanacheRepository (~80% test coverage)
- `REACTIVE_PANACHE_REST` → REST Application with incl. Swagger using ReactivePanacheRepository (~80% test coverage)

The following content is generated based on the entities 

- JPA or Mongo Entities
- DTOs
- Mapper (DTO <-> Entity)
- Repository (interface & implementation)
- Service (interface & implementation)
- REST Resource (incl. swagger)
- UnitTests
- IntegrationTests
- TestUtil per Entity

The following files are generated once 

- pom.xml
- basic index.html page
- application.propperties (test, dev, und prod)
- Exception Handling (muss noch ausgebaut werden)
- RandomGenerator.java (für tests)
- README.md
- docker-compose.xml

### Example XML
> This is the simple example XML try the command `printXml --extended` or create your own

```xml
<entities>
    <entity name="Movie">
        <variables>
            <variable name="name" type="String" required="true" />
            <variable name="date" type="java.time.LocalDate" required="true" />
        </variables>
        <relations>
            <relation name="genre" type="Genre" mapping="ManyToOne" required="true" />
        </relations>
    </entity>
    <entity name="Genre">
        <variables>
            <variable name="name" type="String" required="true" />
        </variables>
    </entity>
</entities>
```

## Getting started

### Building

- build  
    `./mvnw clean package`
- run all tests (integration tests will build a generated Application from the extended XML example)  
    `./mvnw -Pintegration-tests test`

### Running

```bash
$ java -jar target/ggq-1.0.0-SNAPSHOT-runner.jar -h

Usage: <main class> [-hV] [COMMAND]
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  codeGenerator   Generate boilerplate code for Entities
  printXml        Print an example XML for the Code Generator
  xslTransformer  Transform XML by using a XSL stylesheet
  xmlWizzard      CLI Wizzard for creating and updating a XML for the Code Generator → better edit the XML  manualy ;)
```

#### `codeGenerator`  
> How does is the project name chosen?  
→ it is taken by the project dirs las folder (to lowercase)
> Which variable types are allowed? 
> - String
> - Integer
> - Double
> - java.time.LocalDate
> - java.time.LocalDateTime
>
> *Those types are tested*  
> → you can still try other types but you may have to adapt the RandomGenerator for the tests, and to avoid adding the imports define the type incl. package name
```bash
$ java -jar target/ggq-1.0.0-SNAPSHOT-runner.jar codeGenerator -h
Usage: <main class> codeGenerator [-hV] [--basePackage=<basePackage>]
                                  --input=<input> --projectDir=<projectDir>
                                  [--type=<templateType>]
Generate boilerplate code for Entities
      --basePackage=<basePackage>
                        Base package
  -h, --help            Show this help message and exit.
      --input=<input>   XML File containing entities
      --projectDir=<projectDir>
                        Quarkus project directory
      --type=<templateType>
                        template types (valid values: JPA_REST,
                          HIBERNATE_PANACHE_REST, MONGO_PANACHE_REST,
                          MONGO_PANACHE_REST_OIDC, REACTIVE_PANACHE_REST)
  -V, --version         Print version information and exit.
```

## Tutorial

1. Generate an `entities.xml`  

        # Simple example with Movie and Genre
        java -jar target/ggq-1.0.0-SNAPSHOT-runner.jar printXml > entities.xml
        # More complex example Brewery
        java -jar target/ggq-1.0.0-SNAPSHOT-runner.jar printXml --extended > entities.xml

2. Generate Quarkus Application form `entities.xml`  

        java -jar target/ggq-1.0.0-SNAPSHOT-runner.jar codeGenerator \
            --input entities.xml \
            --projectDir experiment \
            --type HIBERNATE_PANACHE_REST \
            --basePackage com.experiment

3. Navigate to the `projectDir` and run the tests

        ./mvnw -Pintegration-tests test
        
4. Check the test coverage `target/site/jacoco/index.html`

5. Start the generated Application in `dev` mode

        ./mvnw quarkus:dev
        
6. Access the Application

        http://localhost:8080 

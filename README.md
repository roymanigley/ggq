# `GGQ` - go go Quarkus
> Im Rahmen des Studiums an der HFTM Grenchen im Fach XML Verarbeitung 2 gilt es ein Beispiel Projekt zu entwickeln welches spezifische XML Technologien beleuchten soll.  
>   
> **Gewählte Technologie:**
> - [XSLT Aufgabe 1](docs/ABOUT_XSLT.md)

## Was ist `GGQ`

GGQ ist ein Code Generator für Quarkus welches als CLI implementiert wurde  

```bash
java -jar ggq-1.0.0-SNAPSHOT-runner.jar codeGenerator

Usage: <main class> codeGenerator [--basePackage=<basePackage>] --input=<input> --projectDir=<projectDir>
Generate boilerplate code for Entities
      --basePackage=<basePackage>
                        Base package
      --input=<input>   XML File containing entities
      --projectDir=<projectDir>
                        Quarkus project directory
```

```bash
java -jar ggq-1.0.0-SNAPSHOT-runner.jar codeGenerator --input=/tmp/entities.xml --projectDir=/tmp/myProject --basePackage=ch.hftm.myproject
```

Folgende Java Klassen werden anhand der `entities` generiert
- JPA Entities
- DTOs
- Mapper (DTO <-> Entity)
- Repository (Interface & Implementierung)
- Service (Interface & Implementierung)
- REST Resource (inkl. swagger)  
- UnitTets
  - TestUtil per Entity
  - Repository
  - Service
  - Rest Resource
- IntegrationTests
  - RestResource
  
Folgende Dateien werden einmalig generiert
- pom.xml
- application.propperties (test, dev, und prod)
- DTO für exceptions (muss noch ausgebaut werden)
- RandomGenerator.java (für tests)
- README.md

> Die zu generierenden Entities werden anhand einer XML Datei vorgegeben, diese sollte folgendem Modell entsprechen  

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

### Getting started
- build  
    `./mvnw clean package`
- run all tests  
    `./mvnw -Pintegration-tests test`
    
### Welche Rolle spielt XSLT ?
> Die Transformation der einzelnen Entities in die jeweiligen Ausgaedateien wird mittels XSL Templates umgesetzt.

#### Beispiel Entity
- Input `src/test/resources/xml/entity.xml`  
    ```xml
    <entity name="JustAnEntity">
        <variables>
            <variable name="lastName" type="String" required="true" />
            <variable name="firstName" type="String" required="false" />
            <variable name="counter" type="Integer" required="false" />
            <variable name="price" type="Double" required="true" />
            <variable name="rabatt" type="Double" required="false" />
            <variable name="date" type="java.time.LocalDate" required="true" />
            <variable name="time" type="java.time.LocalDateTime" required="true" />
        </variables>
        <relations>
            <relation name="genre" type="Genre" mapping="ManyToOne" required="true" />
            <relation name="typ" type="Genre" mapping="ManyToOne" required="false" />
            <relation name="customer" type="Customer" mapping="OneToOne" required="true" />
            <relation name="user" type="User" mapping="OneToOne" required="false" />
        </relations>
    </entity>
    ```
- Template `src/main/resources/xsl/code-gen-java/JPA_REST/entity/entity.xsl`
    ```xml
    <xsl:stylesheet version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
        <xsl:import href="helper.xsl" />
        <xsl:output method="text" omit-xml-declaration="yes" />
        <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.domain;
    
    import java.util.*;
    import javax.persistence.*;
    import javax.validation.constraints.*;
    import java.io.Serializable;
    
    @Entity
    public class <xsl:value-of select="entity/@name" /> implements Serializable {
    
        private static final long serialVersionUID = 1L;
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        <xsl:for-each select="entity/variables/variable">
        @Column(nullable=<xsl:value-of select="@required != 'true'"/>)
        <xsl:if test="@required = 'true'">@NotNull</xsl:if>
        private <xsl:value-of select="concat(@type, ' ', @name)" />;
    
        </xsl:for-each>
    
        <xsl:for-each select="entity/relations/relation">
        <xsl:if test="@required = 'true'">@NotNull&#xa;</xsl:if>@<xsl:value-of select="@mapping" />(optional=<xsl:value-of select="@required != 'true'"/>)
        private <xsl:value-of select="concat(@type, ' ', @name)" />;
        </xsl:for-each>
    
        <xsl:call-template name="getterAndSetter">
            <xsl:with-param name="name" select="'id'" />
            <xsl:with-param name="type" select="'Long'" />
            <xsl:with-param name="entityType" select="/entity/@name" />
        </xsl:call-template>
        <xsl:for-each select="entity/variables/variable">
            <xsl:call-template name="getterAndSetter">
                <xsl:with-param name="name" select="@name" />
                <xsl:with-param name="type" select="@type" />
                <xsl:with-param name="entityType" select="/entity/@name" />
            </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="entity/relations/relation">
            <xsl:call-template name="getterAndSetter">
                <xsl:with-param name="name" select="@name" />
                <xsl:with-param name="type" select="@type" />
                <xsl:with-param name="entityType" select="/entity/@name" />
            </xsl:call-template>
        </xsl:for-each>
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof <xsl:value-of select="entity/@name" />)) {
                return false;
            }
            return id != null &amp;&amp; id.equals(((<xsl:value-of select="entity/@name" />) o).id);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(<xsl:value-of select="entity/@name" />.class, id);
        }
    
        @Override
        public String toString() {
            return "<xsl:value-of select="/entity/@name" />: {" +
                "id: " + id +<xsl:for-each select="entity/variables/variable">
                ", <xsl:value-of select="@name" />: " + <xsl:value-of select="@name" /> +</xsl:for-each><xsl:for-each select="entity/relations/relation">
                ", <xsl:value-of select="@name" />: " + <xsl:value-of select="@name" /> +</xsl:for-each>
            "}";
        }
    }
        </xsl:template>
    </xsl:stylesheet>
    ```
- Output `JustAnEntity.java`
    ```java
    package ch.example.domain;
    
    import java.util.*;
    import javax.persistence.*;
    import javax.validation.constraints.*;
    import java.io.Serializable;
    
    @Entity
    public class JustAnEntity implements Serializable {
    
        private static final long serialVersionUID = 1L;
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        
        @Column(nullable=false) @NotNull
        private String lastName;
        
        @Column(nullable=true) 
        private String firstName;
        
        @Column(nullable=true) 
        private Integer counter;
        
        @Column(nullable=false) @NotNull
        private Double price;
        
        @Column(nullable=true) 
        private Double rabatt;
        
        @Column(nullable=false) @NotNull
        private java.time.LocalDate date;
        
        @Column(nullable=false) @NotNull
        private java.time.LocalDateTime time;
        
        @ManyToOne(optional=false) @NotNull
        private Genre genre;
        
        @ManyToOne(optional=true) 
        private Genre typ;
        
        @OneToOne(optional=false) @NotNull
        private Customer customer;
        
        @OneToOne(optional=true) 
        private User user;
        
        public Long getId() {
            return id;
        }
    
        public void setId(Long id) {
            this.id = id;
        }
    
        public JustAnEntity id(Long id) {
            this.id = id;
            return this;
        }
        
        public String getLastName() {
            return lastName;
        }
    
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    
        public JustAnEntity lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public String getFirstName() {
            return firstName;
        }
    
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    
        public JustAnEntity firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public Integer getCounter() {
            return counter;
        }
    
        public void setCounter(Integer counter) {
            this.counter = counter;
        }
    
        public JustAnEntity counter(Integer counter) {
            this.counter = counter;
            return this;
        }
        
        public Double getPrice() {
            return price;
        }
    
        public void setPrice(Double price) {
            this.price = price;
        }
    
        public JustAnEntity price(Double price) {
            this.price = price;
            return this;
        }
        
        public Double getRabatt() {
            return rabatt;
        }
    
        public void setRabatt(Double rabatt) {
            this.rabatt = rabatt;
        }
    
        public JustAnEntity rabatt(Double rabatt) {
            this.rabatt = rabatt;
            return this;
        }
        
        public java.time.LocalDate getDate() {
            return date;
        }
    
        public void setDate(java.time.LocalDate date) {
            this.date = date;
        }
    
        public JustAnEntity date(java.time.LocalDate date) {
            this.date = date;
            return this;
        }
        
        public java.time.LocalDateTime getTime() {
            return time;
        }
    
        public void setTime(java.time.LocalDateTime time) {
            this.time = time;
        }
    
        public JustAnEntity time(java.time.LocalDateTime time) {
            this.time = time;
            return this;
        }
        
        public Genre getGenre() {
            return genre;
        }
    
        public void setGenre(Genre genre) {
            this.genre = genre;
        }
    
        public JustAnEntity genre(Genre genre) {
            this.genre = genre;
            return this;
        }
        
        public Genre getTyp() {
            return typ;
        }
    
        public void setTyp(Genre typ) {
            this.typ = typ;
        }
    
        public JustAnEntity typ(Genre typ) {
            this.typ = typ;
            return this;
        }
        
        public Customer getCustomer() {
            return customer;
        }
    
        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    
        public JustAnEntity customer(Customer customer) {
            this.customer = customer;
            return this;
        }
        
        public User getUser() {
            return user;
        }
    
        public void setUser(User user) {
            this.user = user;
        }
    
        public JustAnEntity user(User user) {
            this.user = user;
            return this;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof JustAnEntity)) {
                return false;
            }
            return id != null && id.equals(((JustAnEntity) o).id);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(JustAnEntity.class, id);
        }
    
        @Override
        public String toString() {
            return "JustAnEntity: {" +
                "id: " + id +
                ", lastName: " + lastName +
                ", firstName: " + firstName +
                ", counter: " + counter +
                ", price: " + price +
                ", rabatt: " + rabatt +
                ", date: " + date +
                ", time: " + time +
                ", genre: " + genre +
                ", typ: " + typ +
                ", customer: " + customer +
                ", user: " + user +
            "}";
        }
    }
    ```

----

## Beispiel XML

```xml
<entities>
    <entity name="Beer">
        <variables>
            <variable name="name" type="String" required="true" />
            <variable name="price" type="Double" required="true" />
            <variable name="description" type="String" required="true" />
        </variables>
        <relations>
            <relation name="type" type="BeerType" mapping="ManyToOne" required="true" />
        </relations>
    </entity>
    <entity name="BeerType">
        <variables>
            <variable name="name" type="String" required="true" />
        </variables>
    </entity>
    <entity name="Customer">
        <variables>
            <variable name="firstName" type="String" required="true" />
            <variable name="lastName" type="String" required="true" />
        </variables>
    </entity>
    <entity name="BeerOrder">
        <variables>
            <variable name="remark" type="String" required="true" />
            <variable name="deliveryDate" type="java.time.LocalDate" required="true" />
            <variable name="orderDateTime" type="java.time.LocalDateTime" required="true" />
        </variables>
        <relations>
            <relation name="customer" type="Customer" mapping="ManyToOne" required="true" />
            <relation name="employee" type="Employee" mapping="ManyToOne" required="true" />
        </relations>
    </entity>
    <entity name="OrderPosition">
        <variables>
            <variable name="orderCount" type="Double" required="true" />
            <variable name="deliveredCount" required="true" type="Double" />
            <variable name="returnedCount" required="true" type="Double" />
        </variables>
        <relations>
            <relation name="beerOrder" type="BeerOrder" mapping="ManyToOne" required="true" />
        </relations>
    </entity>
    <entity name="CustomerAddress">
        <variables>
            <variable name="recipient" type="String" required="true" />
            <variable name="street" type="String" required="true" />
            <variable name="pc" type="String" required="true" />
            <variable name="city" type="String" required="true" />
        </variables>
        <relations>
            <relation name="customer" type="Customer" mapping="ManyToOne" required="true" />
        </relations>
    </entity>
    <entity name="User">
        <variables>
            <variable name="login" type="String" required="true" />
            <variable name="password" type="String" required="true" />
        </variables>
        <relations>
            <relation name="customer" type="Customer" mapping="OneToOne" required="true" />
        </relations>
    </entity>
    <entity name="Employee">
        <variables>
            <variable name="firstName" type="String" required="true" />
            <variable name="lastName" type="String" required="true" />
        </variables>
        <relations>
            <relation name="customer" type="Customer" mapping="OneToOne" required="true" />
        </relations>
    </entity>
</entities>
```
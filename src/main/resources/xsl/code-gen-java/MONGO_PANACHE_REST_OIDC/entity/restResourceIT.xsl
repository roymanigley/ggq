<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest;

import io.quarkus.panache.common.Page;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.QuarkusTestResource;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.', entity/@name, 'Service')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.util.', entity/@name, 'TestUtil')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.conf.MongoTestResourceLifecycleManager')" />;

@QuarkusTest
@QuarkusTestResource(MongoTestResourceLifecycleManager.class)
public class <xsl:value-of select="entity/@name" />ResourceIT {

    @Inject
    <xsl:value-of select="entity/@name" />Service service;

    private <xsl:value-of select="entity/@name" />Dto record;

    @BeforeEach
    @Transactional
    public void beforeEach() {
        record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto();
    }

    private Integer countRecords() {
        return service.findAll(Page.of(0,999999)).onItem().transform(List::size).await().indefinitely();
    }

    @Test
    public void shouldFindAllRecords() {
        final Integer recordsCount = countRecords();
        given()
        .when().get("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>/?size=999999")
            .then()
            .statusCode(Status.OK.getStatusCode())
            .body("$.size()", is(recordsCount));
    }

    @Test
    public void shouldFindRecordById() {
        record = service.save(record).await().indefinitely();
        given()
            .when().get("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>/" + record.getId())
            .then()
            .statusCode(Status.OK.getStatusCode())
            .body("id", is(record.getId()))<xsl:for-each select="entity/variables/variable[@required = 'true']">
            .body("<xsl:value-of select="@name" />", is(record.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />()<xsl:choose><xsl:when test="@type = 'Double'">.floatValue()</xsl:when><xsl:otherwise> + ""</xsl:otherwise></xsl:choose>))</xsl:for-each>
            <xsl:for-each select="entity/relations/relation[@required = 'true']">
            .body("<xsl:value-of select="ggq:firstToLowerCase(concat(@name, 'Id'))" />", is(record.get<xsl:value-of select="ggq:firstToUpperCase(concat(@name, 'Id'))" />()))
            </xsl:for-each>;
    }

    @Test
    public void shouldNotFindRecordById() {
        given()
        .when().get("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>/" + new ObjectId().toHexString())
            .then()
            .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void shouldUpdateIdAfterCreate() {
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .post("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>")
            .then()
            .statusCode(Status.CREATED.getStatusCode())
            .body("id", notNullValue())<xsl:for-each select="entity/variables/variable[@required = 'true']">
            .body("<xsl:value-of select="@name" />", is(record.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />()<xsl:choose><xsl:when test="@type = 'Double'">.floatValue()</xsl:when><xsl:otherwise> + ""</xsl:otherwise></xsl:choose>))</xsl:for-each>
            <xsl:for-each select="entity/relations/relation[@required = 'true']">
            .body("<xsl:value-of select="ggq:firstToLowerCase(concat(@name, 'Id'))" />", is(record.get<xsl:value-of select="ggq:firstToUpperCase(concat(@name, 'Id'))" />()))
            </xsl:for-each>;
    }

    @Test
    public void shouldFailCreateBecauseIdAlreadyExists() {
        record = service.save(record).await().indefinitely();
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .post("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }

    <xsl:for-each select="entity/variables/variable[@required = 'true']">
    @Test
    public void validationShouldFailOnCreate_missing_<xsl:value-of select="@name" />() {
        record.<xsl:value-of select="@name" />(null);

        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .post("api/<xsl:value-of select="ggq:toLowerCase(/entity/@name)"/>")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }
    </xsl:for-each>
    <xsl:for-each select="entity/variables/variable[@required = 'true']">
    @Test
    public void validationShouldFailOnUpdate_missing_<xsl:value-of select="@name" />() {
        record = service.save(record).await().indefinitely()
            .<xsl:value-of select="@name" />(null);
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .put("api/<xsl:value-of select="ggq:toLowerCase(/entity/@name)"/>")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }
    </xsl:for-each>
    <xsl:for-each select="entity/relations/relation[@required = 'true']">
    @Test
    public void validationShouldFailOnCreate_missing_<xsl:value-of select="@name" />Id() {
        record.<xsl:value-of select="@name" />Id(null);
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .post("api/<xsl:value-of select="ggq:toLowerCase(/entity/@name)"/>")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }
    </xsl:for-each>
    <xsl:for-each select="entity/relations/relation[@required = 'true']">
    @Test
    public void validationShouldFailOnUpdate_missing_<xsl:value-of select="@name" />Id() {
        record = service.save(record).await().indefinitely()
            .<xsl:value-of select="@name" />Id(null);
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .put("api/<xsl:value-of select="ggq:toLowerCase(/entity/@name)"/>")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }
    </xsl:for-each>

    @Test
    public void validationShouldFailOnUpdateNoId() {
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .put("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldUpdate() {
        record = service.save(record).await().indefinitely();
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .put("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>")
            .then()
            .statusCode(Status.OK.getStatusCode())
            .body("id", notNullValue())<xsl:for-each select="entity/variables/variable[@required = 'true']">
            .body("<xsl:value-of select="@name" />", is(record.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />()<xsl:choose><xsl:when test="@type = 'Double'">.floatValue()</xsl:when><xsl:otherwise> + ""</xsl:otherwise></xsl:choose>))</xsl:for-each>
            <xsl:for-each select="entity/relations/relation[@required = 'true']">
            .body("<xsl:value-of select="ggq:firstToLowerCase(concat(@name, 'Id'))" />", is(record.get<xsl:value-of select="ggq:firstToUpperCase(concat(@name, 'Id'))" />()))
            </xsl:for-each>;
    }

    @Test
    public void shouldFailUpdateBecauseIdDoesNotExist() {
        given()
            .when()
            .body(record)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .put("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldDelete() {
        record = service.save(record).await().indefinitely();

        final Integer recordsCountBeforeDelete = countRecords();
        given()
            .when()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .delete("api/<xsl:value-of select="ggq:toLowerCase(entity/@name)"/>/" + record.getId())
            .then()
            .statusCode(Status.ACCEPTED.getStatusCode());

            assertThat(!service.findById(record.getId()).await().indefinitely().isPresent()).isTrue();
            assertThat(recordsCountBeforeDelete ).isGreaterThan(countRecords());
    }
}
    </xsl:template>
</xsl:stylesheet>

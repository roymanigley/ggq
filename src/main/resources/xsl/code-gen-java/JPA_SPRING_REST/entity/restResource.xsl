<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:str="xalan://java.lang.String">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.', entity/@name, 'Service')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;
import <xsl:value-of select="$BASE_PACKAGE" />.web.rest.dto.PageRequest;

@Tag(name = "<xsl:value-of select="concat($BASE_PACKAGE, '.web.rest.', entity/@name)" />Resource")
@RequestMapping("/api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>")
@RestController
public class <xsl:value-of select="entity/@name" />Resource {

    private <xsl:value-of select="entity/@name" />Service service;

    private Logger log;

    public <xsl:value-of select="entity/@name" />Resource(<xsl:value-of select="entity/@name" />Service service, Logger log) {
        this.service = service;
        this.log = log;
    }

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "find all records")
    public Response findAll(@RequestParam(value = "page", required = false) int page, @RequestParam(value = "size", required = false, defaultValue = "25") int size) {
        log.info("GET: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>");
        return Response.ok(service.findAll(new PageRequest(page, size).toPage())).build();
    }

    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "find single record by id")
    public Response findById(@PathVariable("id") Long id) {
        log.info("GET: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>/" + id);
        return service.findById(id)
            .map(record -> Response.ok(record))
            .orElseGet(() -> Response.status(Status.NOT_FOUND))
            .build();
    }

    @PostMapping
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "create a record")
    public Response create(@Valid <xsl:value-of select="entity/@name" />Dto record) {
        log.info("POST: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/> data: " + record);
        return Optional.of(record)
            .filter(r -> r.getId() == null)
            .map(r ->
                Response.status(Status.CREATED).entity(service.save(r))
            )
            .orElseGet(() -> Response.status(Status.BAD_REQUEST))
            .build();
    }

    @PutMapping
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "update a record")
    public Response update(@Valid <xsl:value-of select="entity/@name" />Dto record) {
        log.info("PUT: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/> data: " + record);
        return Optional.of(record)
            .filter(r -> r.getId() != null)
            .map(r ->
                Response.status(Status.OK).entity(service.save(r))
        )
        .orElseGet(() -> Response.status(Status.BAD_REQUEST))
        .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a record")
    public Response delete(@PathVariable("id") Long id) {
        log.info("DELETE: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>/" + id);
        service.delete(id);
        return Response.accepted().build();
    }
}
    </xsl:template>
</xsl:stylesheet>

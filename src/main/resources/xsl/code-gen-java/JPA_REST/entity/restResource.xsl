<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:str="xalan://java.lang.String">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.', entity/@name, 'Service')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;

@Tag(name = "<xsl:value-of select="concat($BASE_PACKAGE, '.web.rest.', entity/@name)" />Resource")
@Path("/api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>")
@RequestScoped
public class <xsl:value-of select="entity/@name" />Resource {

    private <xsl:value-of select="entity/@name" />Service service;

    private Logger log = LoggerFactory.getLogger(<xsl:value-of select="entity/@name" />Resource.class);

    @Inject
    public <xsl:value-of select="entity/@name" />Resource(<xsl:value-of select="entity/@name" />Service service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "find all records")
    public Response findAll() {
        log.info("GET: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "find single record by id")
    public Response findById(@PathParam("id") Long id) {
        log.info("GET: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>/" + id);
        return service.findById(id)
            .map(record -> Response.ok(record))
            .orElseGet(() -> Response.status(Status.NOT_FOUND))
            .build();
    }

    @POST
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

    @PUT
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

    @DELETE
    @Path("/{id}")
    @Operation(summary = "delete a record")
    public Response delete(@PathParam("id") Long id) {
        log.info("DELETE: /api/<xsl:value-of select="str:toLowerCase(str:new(entity/@name))"/>/" + id);
        service.delete(id);
        return Response.accepted().build();
    }
}
    </xsl:template>
</xsl:stylesheet>

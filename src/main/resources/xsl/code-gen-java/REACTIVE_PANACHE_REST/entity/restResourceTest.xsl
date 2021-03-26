<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" omit-xml-declaration="yes" />
<xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.quarkus.panache.common.Page;
import javax.ws.rs.core.Response;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.', entity/@name, 'Service')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.util.', entity/@name, 'TestUtil')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.web.rest.dto.PageRequest')" />;

import java.util.Collections;
import java.util.Optional;

public class <xsl:value-of select="entity/@name" />ResourceTest {

    private static final Logger LOG = Logger.getLogger(<xsl:value-of select="entity/@name" />ResourceTest.class);

    private PageRequest DEFAULT_PAGE_REQUEST;

    @Mock
    private <xsl:value-of select="entity/@name" />Service service;
    private <xsl:value-of select="entity/@name" />Resource resource;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        DEFAULT_PAGE_REQUEST = new PageRequest();
        DEFAULT_PAGE_REQUEST.setPage(0);
        DEFAULT_PAGE_REQUEST.setSize(25);
        resource = new <xsl:value-of select="entity/@name" />Resource(service, LOG);
    }

    @Test
    public void findAllRecordsShouldBeCalled() {
        when(service.findAll(any())).thenReturn(Uni.createFrom().item(Collections.EMPTY_LIST));
        final Response response = resource.findAll(DEFAULT_PAGE_REQUEST).await().indefinitely();
        verify(service).findAll(any(Page.class));
        assertThat(response.getStatus()).isEqualTo(200);
        verify(service, never()).findById(any());
        verify(service, never()).save(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void findByIdShouldBeCalled() {
        when(service.findById(1L)).thenReturn(Uni.createFrom().item(Optional.of(new <xsl:value-of select="entity/@name" />Dto())));
        final Response response = resource.findById(1L).await().indefinitely();
        assertThat(response.getStatus()).isEqualTo(200);
        verify(service).findById(any());
        verify(service, never()).findAll(any(Page.class));
        verify(service, never()).save(any());
        verify(service, never()).delete(any());
    }


    @Test
    public void whenNoRecordFoundExceptionDTOShouldBeReturned() {
        when(service.findById(1L)).thenReturn(Uni.createFrom().item(Optional.empty()));
        final Response response = resource.findById(1L).await().indefinitely();
        assertThat(response.getStatus()).isEqualTo(404);
        verify(service).findById(any());
        verify(service, never()).findAll(any(Page.class));
        verify(service, never()).save(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenCreateSaveRecordShouldBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        when(service.save(any())).thenReturn(Uni.createFrom().item(record));
        final Response response = resource.create(record).await().indefinitely();
        assertThat(response.getStatus()).isEqualTo(201);
        verify(service).save(any());
        verify(service, never()).findAll(any(Page.class));
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenCreateSaveRecordShouldNotBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        record.setId(1L);
        final Response response = resource.create(record).await().indefinitely();
        assertThat(response.getStatus()).isEqualTo(400);
        verify(service, never()).save(any());
        verify(service, never()).findAll(any(Page.class));
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenUpdateSaveRecordShouldBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        record.setId(1L);
        when(service.save(any())).thenReturn(Uni.createFrom().item(record));
        final Response response = resource.update(record).await().indefinitely();;
        assertThat(response.getStatus()).isEqualTo(200);
        verify(service).save(any());
        verify(service, never()).findAll(any(Page.class));
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenUpdateSaveRecordShouldNotBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        final Response response = resource.update(record).await().indefinitely();
        assertThat(response.getStatus()).isEqualTo(400);
        verify(service, never()).save(any());
        verify(service, never()).findAll(any(Page.class));
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void deleteRecordShouldBeCalled() {
        when(service.delete(anyLong())).thenReturn(Uni.createFrom().item("").onItem().apply(s -> null));
        final Response response = resource.delete(1L).await().indefinitely();
        assertThat(response.getStatus()).isEqualTo(202);
        verify(service).delete(any());
        verify(service, never()).findAll(any(Page.class));
        verify(service, never()).findById(any());
        verify(service, never()).save(any());
    }
}
    </xsl:template>
</xsl:stylesheet>

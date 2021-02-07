<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" omit-xml-declaration="yes" />
<xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.', entity/@name, 'Service')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.util.', entity/@name, 'TestUtil')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.web.rest.exceptions.ExceptionDto')" />;

import java.util.Optional;

public class <xsl:value-of select="entity/@name" />ResourceTest {

    @Mock
    private <xsl:value-of select="entity/@name" />Service service;
    private <xsl:value-of select="entity/@name" />Resource resource;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        resource = new <xsl:value-of select="entity/@name" />Resource(service);
    }

    @Test
    public void findAllRecordsShouldBeCalled() {
        final Response response = resource.findAll();
        verify(service).findAll();
        assertThat(response.getStatus()).isEqualTo(200);
        verify(service, never()).findById(any());
        verify(service, never()).save(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void findByIdShouldBeCalled() {
        when(service.findById(1L)).thenReturn(Optional.of(new <xsl:value-of select="entity/@name" />Dto()));
        final Response response = resource.findById(1L);
        assertThat(response.getStatus()).isEqualTo(200);
        verify(service).findById(any());
        verify(service, never()).findAll();
        verify(service, never()).save(any());
        verify(service, never()).delete(any());
    }


    @Test
    public void whenNoRecordFoundExceptionDTOShouldBeReturned() {
        when(service.findById(1L)).thenReturn(Optional.empty());
        final Response response = resource.findById(1L);
        assertThat(response.getStatus()).isEqualTo(404);
        verify(service).findById(any());
        verify(service, never()).findAll();
        verify(service, never()).save(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenCreateSaveRecordShouldBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        final Response response = resource.create(record);
        assertThat(response.getStatus()).isEqualTo(201);
        verify(service).save(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenCreateSaveRecordShouldNotBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        record.setId(1L);
        final Response response = resource.create(record);
        assertThat(response.getStatus()).isEqualTo(400);
        verify(service, never()).save(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenCreateFailExceptionDTOShouldBeReturned() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        when(service.save(any(<xsl:value-of select="entity/@name" />Dto.class))).thenThrow(ConstraintViolationException.class);
        final Response response = resource.create(record);
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getEntity()).isInstanceOf(ExceptionDto.class);
        verify(service).save(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenUpdateSaveRecordShouldBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        record.setId(1L);
        final Response response = resource.update(record);
        assertThat(response.getStatus()).isEqualTo(200);
        verify(service).save(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenUpdateSaveRecordShouldNotBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        final Response response = resource.update(record);
        assertThat(response.getStatus()).isEqualTo(400);
        verify(service, never()).save(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void whenUpdateFailExceptionDTOShouldBeReturned() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        record.setId(1L);
        when(service.save(any(<xsl:value-of select="entity/@name" />Dto.class))).thenThrow(ConstraintViolationException.class);
        final Response response = resource.update(record);
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getEntity()).isInstanceOf(ExceptionDto.class);
        verify(service).save(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).delete(any());
    }

    @Test
    public void deleteRecordShouldBeCalled() {
        final Response response = resource.delete(1L);
        assertThat(response.getStatus()).isEqualTo(202);
        verify(service).delete(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).save(any());
    }

    @Test
    public void whenDeleteFailExceptionDTOShouldBeReturned() {
        doThrow(ConstraintViolationException.class).when(service).delete(1L);
        final Response response = resource.delete(1L);
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getEntity()).isInstanceOf(ExceptionDto.class);
        verify(service).delete(any());
        verify(service, never()).findAll();
        verify(service, never()).findById(any());
        verify(service, never()).save(any());
    }
}
    </xsl:template>
</xsl:stylesheet>
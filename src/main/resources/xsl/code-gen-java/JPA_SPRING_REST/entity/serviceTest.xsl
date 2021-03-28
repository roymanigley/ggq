<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.impl.', entity/@name, 'ServiceImpl')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.mapper.', entity/@name, 'Mapper')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.repository.', entity/@name, 'Repository')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.util.', entity/@name, 'TestUtil')" />;

import java.util.Collections;
import java.util.Optional;

public class <xsl:value-of select="entity/@name" />ServiceTest {

    private static final Pageable DEFAULT_PAGE = new PageRequest(0, 25);

    @Mock
    private <xsl:value-of select="entity/@name" />Repository repository;
    private <xsl:value-of select="entity/@name" />Service service;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        service = new <xsl:value-of select="entity/@name" />ServiceImpl(repository);
    }

    @Test
    public void findAllRecordsShouldBeCalled() {
        when(repository.findAll(DEFAULT_PAGE)).thenReturn(Page.empty());
        service.findAll(DEFAULT_PAGE);
        verify(repository).findAll(DEFAULT_PAGE);
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
        verify(repository, never()).deleteById(any());
    }

    @Test
    public void findByIdShouldBeCalled() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        service.findById(1L);
        verify(repository).findById(any());
        verify(repository, never()).findAll(DEFAULT_PAGE);
        verify(repository, never()).save(any());
        verify(repository, never()).deleteById(any());
    }

    @Test
    public void saveRecordShouldBeCalled() {
        final <xsl:value-of select="entity/@name" />Dto record = <xsl:value-of select="entity/@name" />TestUtil.createRandomDto(null);
        when(repository.save(any())).thenReturn(<xsl:value-of select="entity/@name" />Mapper.toEntity(record));
        service.save(record);
        verify(repository).save(any());
        verify(repository, never()).findAll(DEFAULT_PAGE);
        verify(repository, never()).findById(any());
        verify(repository, never()).deleteById(any());
    }

    @Test
    public void deleteByIdRecordShouldBeCalled() {
        service.delete(1L);
        verify(repository).deleteById(any());
        verify(repository, never()).findAll(DEFAULT_PAGE);
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }
}
    </xsl:template>
</xsl:stylesheet>

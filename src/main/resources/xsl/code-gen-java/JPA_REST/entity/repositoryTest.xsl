<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.repository;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.repository.impl.', entity/@name)" />RepositoryImpl;
import <xsl:value-of select="concat($BASE_PACKAGE, '.util.', entity/@name, 'TestUtil')" />;
    
public class <xsl:value-of select="entity/@name" />RepositoryTest {

    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery&lt;<xsl:value-of select="entity/@name" />&gt; query;
    private <xsl:value-of select="entity/@name" />Repository repository;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        when(em.createQuery(anyString(), any(Class.class)))
        .thenReturn(query);
        repository = new <xsl:value-of select="entity/@name" />RepositoryImpl(em);
    }

    @Test
    public void findAllRecordsShouldBeCalled() {
        repository.findAll();
        verify(em).createQuery(anyString(), any(Class.class));
    }

    @Test
    public void findByIdShouldBeCalled() {
        repository.findById(1L);
        verify(em).find(<xsl:value-of select="entity/@name" />.class, 1L);
    }

    @Test
    public void saveRecordShouldBeCalled() {
        final <xsl:value-of select="entity/@name" /> record = <xsl:value-of select="entity/@name" />TestUtil.createRandomRecord(null);
        repository.save(record);
        verify(em).merge(record);
    }

    @Test
    public void deleteAndFindRecordShouldBeCalled() {
        when(em.find(any(Class.class), anyLong())).thenReturn(<xsl:value-of select="entity/@name" />TestUtil.createRandomRecord(null));
        repository.delete(1L);
        verify(em).find(any(Class.class), anyLong());
        verify(em).remove(any());
    }

    @Test
    public void deleteAndWithOutFindRecordShouldBeCalled() {
        repository.delete(1L);
        verify(em).find(any(Class.class), anyLong());
        verify(em, never()).remove(any());
    }
}
    </xsl:template>
</xsl:stylesheet>
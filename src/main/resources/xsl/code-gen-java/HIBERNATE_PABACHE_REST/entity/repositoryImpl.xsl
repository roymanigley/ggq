<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.repository.impl;

import java.util.List;
import java.util.Optional;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import io.quarkus.panache.common.Page;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.mapper.', entity/@name, 'Mapper')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.repository.', entity/@name)" />Repository;

@ApplicationScoped
public class <xsl:value-of select="entity/@name" />RepositoryImpl implements PanacheRepository&lt;<xsl:value-of select="entity/@name" />&gt;, <xsl:value-of select="entity/@name" />Repository {

    @Override
    public List&lt;<xsl:value-of select="entity/@name" />&gt; findAll(Page page) {
        return findAll().page(page).list();
    }

    @Transactional
    @Override
    public Optional&lt;<xsl:value-of select="entity/@name" />&gt; findRecordById(Long id) {
        return findByIdOptional(id);
    }

    @Transactional
    @Override
    public <xsl:value-of select="entity/@name" /> save(<xsl:value-of select="entity/@name" /> record) {
        <xsl:value-of select="entity/@name" /> managed;
        if (record.getId() != null) {
            managed = findByIdOptional(record.getId()).orElseGet(<xsl:value-of select="entity/@name" />::new);
            <xsl:value-of select="entity/@name" />Mapper.mergeEntities(managed, record);
        } else {
            managed = record;
        }
        persist(managed);
        return managed;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        deleteById(id);
    }
}
    </xsl:template>
</xsl:stylesheet>
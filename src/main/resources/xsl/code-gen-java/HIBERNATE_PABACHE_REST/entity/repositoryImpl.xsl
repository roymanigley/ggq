<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.repository.impl;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.repository.', entity/@name)" />Repository;

@ApplicationScoped
public class <xsl:value-of select="entity/@name" />RepositoryImpl implements <xsl:value-of select="entity/@name" />Repository {

    @PersistenceContext
    EntityManager em;

    @Inject
    public <xsl:value-of select="entity/@name" />RepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public <xsl:value-of select="entity/@name" />RepositoryImpl() { }

    @Override
    @Transactional
    public List&lt;<xsl:value-of select="entity/@name" />&gt; findAll() {
        return em.createQuery("from <xsl:value-of select="entity/@name" />", <xsl:value-of select="entity/@name" />.class)
            .getResultList();
    }

    @Override
    @Transactional
    public Optional&lt;<xsl:value-of select="entity/@name" />&gt; findById(Long id) {
        return (Optional&lt;<xsl:value-of select="entity/@name" />&gt;) Optional.ofNullable(em.find(<xsl:value-of select="entity/@name" />.class, id));
    }

    @Override
    @Transactional
    public <xsl:value-of select="entity/@name" /> save(<xsl:value-of select="entity/@name" /> record) {
        return em.merge(record);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id).ifPresent(em::remove);
    }
}
    </xsl:template>
</xsl:stylesheet>
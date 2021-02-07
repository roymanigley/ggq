<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.repository.', entity/@name)" />Repository;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.', entity/@name, 'Service')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.mapper.', entity/@name, 'Mapper')" />;

@ApplicationScoped
public class <xsl:value-of select="entity/@name" />ServiceImpl implements <xsl:value-of select="entity/@name" />Service {

    private <xsl:value-of select="entity/@name" />Repository repository;

    @Inject
    public <xsl:value-of select="entity/@name" />ServiceImpl(<xsl:value-of select="entity/@name" />Repository repository) {
        this.repository = repository;
    }

    @Transactional
    public List&lt;<xsl:value-of select="entity/@name" />Dto&gt; findAll() {
        return repository.findAll().stream()
            .map(<xsl:value-of select="entity/@name" />Mapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public Optional&lt;<xsl:value-of select="entity/@name" />Dto&gt; findById(Long id) {
        return repository.findById(id)
            .map(<xsl:value-of select="entity/@name" />Mapper::toDto);
    }

    @Transactional
    public <xsl:value-of select="entity/@name" />Dto save(<xsl:value-of select="entity/@name" />Dto record) {
        <xsl:value-of select="entity/@name" /> entity = <xsl:value-of select="entity/@name" />Mapper.toEntity(record);
        entity = repository.save(entity);
        return <xsl:value-of select="entity/@name" />Mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }
}
    </xsl:template>
</xsl:stylesheet>
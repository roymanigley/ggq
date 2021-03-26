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
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;

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

    @Override
    public Uni&lt;List&lt;<xsl:value-of select="entity/@name" />Dto&gt;&gt; findAll(Page page) {
        return repository.findAll(page)
            .onItem().transform(records -> records.stream()
                .map(<xsl:value-of select="entity/@name" />Mapper::toDto)
                .collect(Collectors.toList())
            );
    }

    @Override
    public Uni&lt;Optional&lt;<xsl:value-of select="entity/@name" />Dto&gt;&gt; findById(Long id) {
        return repository.findRecordById(id)
            .onItem()
                .transform(Optional::ofNullable)
                .map(optional -> optional.map(<xsl:value-of select="entity/@name" />Mapper::toDto));
    }

    @Override
    public Uni&lt;<xsl:value-of select="entity/@name" />Dto&gt; save(<xsl:value-of select="entity/@name" />Dto record) {
        final <xsl:value-of select="entity/@name" /> fromDto = <xsl:value-of select="entity/@name" />Mapper.toEntity(record);
        return repository.save(fromDto)
            .map(v -> record.id(fromDto.getId()));
    }

    @Override
    public Uni&lt;Boolean&gt; delete(Long id) {
        return repository.delete(id);
    }
}
    </xsl:template>
</xsl:stylesheet>

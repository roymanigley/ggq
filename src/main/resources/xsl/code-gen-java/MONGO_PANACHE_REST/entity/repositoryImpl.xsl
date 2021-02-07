<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.repository.impl;

import java.util.List;
import java.util.Optional;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.repository.', entity/@name)" />Repository;

@ApplicationScoped
public class <xsl:value-of select="entity/@name" />RepositoryImpl implements ReactivePanacheMongoRepository&lt;<xsl:value-of select="entity/@name" />&gt;, <xsl:value-of select="entity/@name" />Repository {

    @Override
    public Uni&lt;List&lt;<xsl:value-of select="entity/@name" />&gt;&gt; findAll(Page page) {
        return findAll().page(page).list();
    }

    @Override
    public Uni&lt;Optional&lt;<xsl:value-of select="entity/@name" />&gt;&gt; findRecordById(String id) {
        return findByIdOptional(new ObjectId(id));
    }

    @Override
    public Uni&lt;Void&gt; save(<xsl:value-of select="entity/@name" /> record) {
        return persistOrUpdate(record);
    }

    @Override
    public Uni&lt;Boolean&gt; delete(String id) {
        return deleteById(new ObjectId(id));
    }
}
    </xsl:template>
</xsl:stylesheet>

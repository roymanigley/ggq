<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="helper.xsl" />
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.domain;

import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.types.ObjectId;

import java.util.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@MongoEntity(collection = "<xsl:value-of select="entity/@name" />-collection")
public class <xsl:value-of select="entity/@name" /> implements Serializable {

    private static final long serialVersionUID = 1L;

    private ObjectId id;
    <xsl:for-each select="entity/variables/variable">
    <xsl:if test="@required = 'true'">@NotNull</xsl:if>
    private <xsl:value-of select="concat(@type, ' ', @name)" />;
    </xsl:for-each>

    <xsl:for-each select="entity/relations/relation">
    <xsl:if test="@required = 'true'">@NotNull</xsl:if>
    private <xsl:value-of select="concat(@type, ' ', @name)" />;
    </xsl:for-each>

    <xsl:call-template name="getterAndSetter">
        <xsl:with-param name="name" select="'id'" />
        <xsl:with-param name="type" select="'ObjectId'" />
        <xsl:with-param name="entityType" select="/entity/@name" />
    </xsl:call-template>

    public <xsl:value-of select="entity/@name" /> id(String id) {
        if (id != null) {
            this.id = new ObjectId(id);
        }
        return this;
    }

    <xsl:for-each select="entity/variables/variable">
        <xsl:call-template name="getterAndSetter">
            <xsl:with-param name="name" select="@name" />
            <xsl:with-param name="type" select="@type" />
            <xsl:with-param name="entityType" select="/entity/@name" />
        </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="entity/relations/relation">
        <xsl:call-template name="getterAndSetter">
            <xsl:with-param name="name" select="@name" />
            <xsl:with-param name="type" select="@type" />
            <xsl:with-param name="entityType" select="/entity/@name" />
        </xsl:call-template>
    </xsl:for-each>
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof <xsl:value-of select="entity/@name" />)) {
            return false;
        }
        return id != null &amp;&amp; id.equals(((<xsl:value-of select="entity/@name" />) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(<xsl:value-of select="entity/@name" />.class, id);
    }

    @Override
    public String toString() {
        return "<xsl:value-of select="/entity/@name" />: {" +
            "id: " + id +<xsl:for-each select="entity/variables/variable">
            ", <xsl:value-of select="@name" />: " + <xsl:value-of select="@name" /> +</xsl:for-each><xsl:for-each select="entity/relations/relation">
            ", <xsl:value-of select="@name" />: " + <xsl:value-of select="@name" /> +</xsl:for-each>
        "}";
    }
}
</xsl:template>
</xsl:stylesheet>

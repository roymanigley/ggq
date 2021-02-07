<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="helper.xsl" />
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.json.bind.annotation.JsonbDateFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.io.Serializable;

public class <xsl:value-of select="entity/@name" />Dto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    <xsl:for-each select="entity/variables/variable">
    <xsl:if test="@type = 'java.time.LocalDate'">@JsonbDateFormat("yyyy-MM-dd") @JsonFormat(pattern = "yyyy-MM-dd")</xsl:if><xsl:if test="@type = 'java.time.LocalDateTime'">@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")</xsl:if><xsl:if test="@required = 'true'">@NotNull</xsl:if>
    private <xsl:value-of select="concat(@type, ' ', @name)" />;

    </xsl:for-each>

    <xsl:for-each select="entity/relations/relation">
    <xsl:if test="@required = 'true'">@NotNull</xsl:if>
    private Long <xsl:value-of select="@name" />Id;

    </xsl:for-each>

    <xsl:call-template name="getterAndSetter">
        <xsl:with-param name="name" select="'id'" />
        <xsl:with-param name="type" select="'Long'" />
        <xsl:with-param name="entityType" select="concat(/entity/@name, 'Dto')" />
    </xsl:call-template>
    <xsl:for-each select="entity/variables/variable">
        <xsl:call-template name="getterAndSetter">
            <xsl:with-param name="name" select="@name" />
            <xsl:with-param name="type" select="@type" />
            <xsl:with-param name="entityType" select="concat(/entity/@name, 'Dto')" />
        </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="entity/relations/relation">
        <xsl:call-template name="getterAndSetter">
            <xsl:with-param name="name" select="concat(@name, 'Id')" />
            <xsl:with-param name="type" select="'Long'" />
            <xsl:with-param name="entityType" select="concat(/entity/@name, 'Dto')" />
        </xsl:call-template>
    </xsl:for-each>

    @Override
    public String toString() {
        return "<xsl:value-of select="/entity/@name" />Dto: {" +
            "id: " + id +<xsl:for-each select="entity/variables/variable">
            ", <xsl:value-of select="@name" />: " + <xsl:value-of select="@name" /> +</xsl:for-each><xsl:for-each select="entity/relations/relation">
            ", <xsl:value-of select="@name" />Id: " + <xsl:value-of select="@name" />Id +</xsl:for-each>
        "}";
    }
}
</xsl:template>
</xsl:stylesheet>
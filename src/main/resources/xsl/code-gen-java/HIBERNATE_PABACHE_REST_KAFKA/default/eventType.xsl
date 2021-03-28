<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="helper.xsl" />
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.dto.enumerations;

public enum EventType {
    CREATE("CREATE"), UPDATE("CREATE"), DELETE("CREATE");

    private final String s;
    EventType(String s) { this.s = s; }

    @Override
    public String toString() {
        return s;
    }
}
</xsl:template>
</xsl:stylesheet>

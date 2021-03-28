<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="helper.xsl" />
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.dto;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.enumerations.EventType')" />;

public class <xsl:value-of select="entity/@name" />Event {

    // when not public Jsonb serialize this value always to null...
    public String eventType;
    private <xsl:value-of select="entity/@name" /> entity;

    public static <xsl:value-of select="entity/@name" />Event newCreateEventFor(<xsl:value-of select="entity/@name" /> entity) {
        return new <xsl:value-of select="entity/@name" />Event().eventType(EventType.CREATE).entity(entity);
    }

    public static <xsl:value-of select="entity/@name" />Event newUpdateEventFor(<xsl:value-of select="entity/@name" /> entity) {
        return new <xsl:value-of select="entity/@name" />Event().eventType(EventType.UPDATE).entity(entity);
    }

    public static <xsl:value-of select="entity/@name" />Event newDeleteEventFor(<xsl:value-of select="entity/@name" /> entity) {
        return new <xsl:value-of select="entity/@name" />Event().eventType(EventType.DELETE).entity(entity);
    }

    public String getEventType(String eventType) {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    private <xsl:value-of select="entity/@name" />Event eventType(EventType eventType) {
        this.eventType = eventType.toString();
        return this;
    }

    public <xsl:value-of select="entity/@name" /> getEntity() {
        return entity;
    }

    public void setEntity(<xsl:value-of select="entity/@name" /> entity) {
        this.entity = entity;
    }

    private <xsl:value-of select="entity/@name" />Event entity(<xsl:value-of select="entity/@name" /> entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public String toString() {
        return "BeerTypeEvent [eventType=" + eventType + ", entity=" + entity + "]";
    }
}
</xsl:template>
</xsl:stylesheet>

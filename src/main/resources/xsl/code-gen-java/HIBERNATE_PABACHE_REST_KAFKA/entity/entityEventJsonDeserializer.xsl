<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="helper.xsl" />
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.dto.serializers;

import java.io.ByteArrayInputStream;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.apache.kafka.common.serialization.Deserializer;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Event')" />;

public class <xsl:value-of select="entity/@name" />EventJsonDeserializer implements Deserializer&lt;<xsl:value-of select="entity/@name" />Event&gt; {

    private Jsonb jsonB = JsonbBuilder.create();

    @Override
    public <xsl:value-of select="entity/@name" />Event deserialize(String topic, byte[] data) {
        return jsonB.fromJson(new ByteArrayInputStream(data), <xsl:value-of select="entity/@name" />Event.class);
    }
}
</xsl:template>
</xsl:stylesheet>

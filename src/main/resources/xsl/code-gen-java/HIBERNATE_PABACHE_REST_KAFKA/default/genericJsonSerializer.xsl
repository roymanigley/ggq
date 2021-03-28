<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="helper.xsl" />
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.dto.serializers;

import java.io.ByteArrayInputStream;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.apache.kafka.common.serialization.Serializer;

public class GenericJsonSerializer&lt;T&gt; implements Serializer&lt;T&gt; {

    private Jsonb jsonB = JsonbBuilder.create();

    @Override
    public byte[] serialize(String topic, T data) {
        return jsonB.toJson(data).getBytes();
    }
}
</xsl:template>
</xsl:stylesheet>

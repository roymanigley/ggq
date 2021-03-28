<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest.dto;

import javax.ws.rs.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;

public class PageRequest {

    private int page;

    private int size;

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public long getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Pageable toPage() {
        return new org.springframework.data.domain.PageRequest(page, size);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
            "page=" + page +
            ", size=" + size +
            '}';
    }
}
    </xsl:template>
</xsl:stylesheet>

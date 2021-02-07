<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest.exceptions;

import javax.persistence.PersistenceException;

public class ExceptionDto {

    private String errorKey;
    private String errorType;
    private String errorMessage;

    private ExceptionDto() { }

    public static ExceptionDto from(Exception e) {
        ExceptionDto dto = new ExceptionDto();
        if (e.getCause() != null &amp;&amp; e.getCause() instanceof PersistenceException) {
            dto.errorKey = "error.constraint-violation";
            dto.errorType = e.getCause().getCause().getClass().toString();
            dto.errorMessage = e.getCause().getCause().getMessage();
        } else {
            dto.errorKey = "error.unknown";
            dto.errorType = e.getClass().toString();
            dto.errorMessage = e.getMessage();
        }
        return dto;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @Override
    public String toString() {
        return "ExceptionDto: {" +
        "errorKey: " + errorKey +
        ", errorType: " + errorType +
        ", errorMessage: " + errorMessage +
        "}";
    }
}
    </xsl:template>
</xsl:stylesheet>
<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.web.rest.exceptions;

import org.jboss.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.inject.Inject;

@Provider
public class ExceptionHandler implements ExceptionMapper&lt;Throwable&gt; {

    @Inject
    Logger log;

    @Override
    public Response toResponse(Throwable e) {
        log.error("Exception occurred", e );
        return Response.serverError().entity(ExceptionDto.from(e)).build();
    }

    public static class  ExceptionDto {

        private String errorKey = "unknown.error";
        private String errorType;
        private String errorMessage;

        private ExceptionDto() { }

        public static ExceptionDto from(Throwable e) {
            ExceptionDto dto = new ExceptionDto();
            if (e != null &amp;&amp; e.getMessage() != null) {
                dto.errorType = e.getClass().getName();
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
}
    </xsl:template>
</xsl:stylesheet>
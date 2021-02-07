<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:str="xalan://java.lang.String">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class RandomGenerator {

    public static BigInteger getRandomBigInt() {
        return new BigInteger(40, new SecureRandom());
    }

    public static String getRandomString() {
        return getRandomBigInt().toString(32);
    }

    public static Double getRandomDouble() {
        return getRandomBigInt().doubleValue();
    }

    public static Integer getRandomInteger() {
        return getRandomBigInt().intValue();
    }

    public static Long getRandomLong() {
        return getRandomBigInt().longValue();
    }

    public static Float getRandomFloat() {
        return getRandomBigInt().floatValue();
    }

    public static Short getRandomShort() {
        return getRandomBigInt().shortValue();
    }

    public static byte[] getRandomBytes() {
        return getRandomBigInt().toByteArray();
    }

    public static Date getRandomDate() {
        return new Date(getRandomLong());
    }

    public static LocalDate getRandomLocalDate() {
        final int month = (Math.abs(getRandomShort()) % 12) + 1;
        final int day = (Math.abs(getRandomShort()) % 28) + 1;
        return LocalDate.of(2020 + Math.abs(getRandomShort()), month, day);
    }


    public static LocalDateTime getRandomLocalDateTime() {
        final int month = (Math.abs(getRandomShort()) % 12) + 1;
        final int day = (Math.abs(getRandomShort()) % 28) + 1;
        return LocalDateTime.of(getRandomLocalDate(), getRandomLocalTime());
    }

    public static LocalTime getRandomLocalTime() {
        return LocalTime.of(Math.abs(getRandomShort()) % 24, Math.abs(getRandomShort()) % 60, Math.abs(getRandomShort()) % 60);
    }
}
    </xsl:template>
</xsl:stylesheet>
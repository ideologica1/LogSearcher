<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

    <xsl:template match="xmlModel">
        <table border="1">
            <tr>
                <th>Date</th>
                <th>Location</th>
                <th>Content</th>
            </tr>
            <xsl:for-each select="searchInfoResult/resultLogsList">
                <tr>
                    <td><xsl:value-of select="TimeMoment"/></td>
                    <td><xsl:value-of select="FileName"/></td>
                    <td><xsl:value-of select="Content"/></td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

</xsl:stylesheet>
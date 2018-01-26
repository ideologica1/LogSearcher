<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

    <xsl:template match="xmlModel">
        <table border="0" width = "40%" align = "right">
            <tr>
                <td>
                    Расположение логов
                </td>
                <td>
                    <xsl:value-of select="searchInfo/location"/>
                </td>
            </tr>
            <tr>
                <td>
                    Регулярное выражение
                </td>
                <td>
                    <xsl:value-of select="searchInfo/regularExpression"/>
                </td>
            </tr>
            <tr>
                <td>
                    Временные промежутки
                </td>
                <td>
                    <table border = "0" width="100%">
                        <xsl:for-each select="searchInfo/dateInterval">
                            <tr>
                                <td>
                                    Начало <xsl:value-of select="dateFromString"/>
                                </td>
                                <td alighn = "right">
                                    Конец <xsl:value-of select="dateToString"/>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </td>
            </tr>
        </table>

        <xsl:if test="searchInfoResult/resultLogsList">
            <table border="1" >
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
        </xsl:if>
        <xsl:if test="not(searchInfoResult/resultLogsList)">
            <xsl:value-of select="searchInfoResult/emptyResultMessage"/>
        </xsl:if>

    </xsl:template>

</xsl:stylesheet>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="xmlModel">

        Приложение, которому мы все обязаны:
        <xsl:value-of select="applicationName"/>

        Автор:
        <xsl:value-of select="author"/> ООО "Сиблион"

        Регулярное выражение:
        <xsl:value-of select="searchInfo/regularExpression"/>

        Расположение логов:
        <xsl:value-of select="searchInfo/location"/>

        Временные промежутки:
        <xsl:for-each select="searchInfo/dateInterval">


            <xsl:value-of select="dateFromString"/>

            <xsl:value-of select="dateToString"/>

        </xsl:for-each>



        <xsl:for-each select="searchInfoResult/resultLogsList">

            <xsl:value-of select="TimeMoment"/>

            <xsl:value-of select="FileName"/>

            <xsl:value-of select="Content"/>

        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
<?xml version="1.0"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="xmlModel">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="1cm" margin-right="1cm">
                    <fo:region-body margin-top="0mm" margin-bottom="10mm"
                                    margin-left="0mm" margin-right="0mm"/>
                    <fo:region-after extent="10mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="simpleA4" font-family="Arial">
                <fo:static-content flow-name="xsl-region-after">
                    <!-- Добавление номера страницы к нижнему колонтитулу -->
                    <fo:block text-align="end"
                              font-size="10pt"
                              font-family="Arial">
                        Page
                        <fo:page-number/>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <xsl:apply-templates select="searchInfo"/>
                    </fo:block>
                    <fo:block font-size="16pt" font-weight="bold" space-after="20mm" space-before="20mm"
                              text-align="center" padding-before="45%">
                        Приложение, которому мы все обязаны:
                        <xsl:value-of select="applicationName"/>
                    </fo:block>
                    <fo:block font-size="16pt" space-after="5mm" font-family="Arial" text-align="center"
                              page-break-after="always">
                        Автор:
                        <xsl:value-of select="author"/> ООО "Сиблион"
                    </fo:block>
                    <fo:block>
                        <xsl:if test="searchInfoResult/resultLogsList">
                            <xsl:apply-templates select="searchInfoResult" />
                        </xsl:if>
                        <xsl:if test="not(searchInfoResult/resultLogsList)">
                            <xsl:value-of select="searchInfoResult/emptyResultMessage"/>
                        </xsl:if>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="searchInfo">
        <fo:block font-size="12pt">
            <xsl:value-of select="location"/>
            <xsl:value-of select="regularExpression"/>
            <xsl:value-of select="dateInterval"/>
        </fo:block>
    </xsl:template>

    <xsl:template match = "searchInfoResult">
        <fo:table width="100%">
            <fo:table-column column-width="17%" />
            <fo:table-column column-width="23%" />
            <fo:table-column column-width="60%" />
            <fo:table-header>
                <fo:table-row>
                    <fo:table-cell border="2px solid black">
                        <fo:block>
                            Дата
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border="2px solid black">
                        <fo:block>
                            Расположение
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border="2px solid black">
                        <fo:block>
                            Содержание
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-header>
            <fo:table-body>
                <xsl:apply-templates select="resultLogsList"/>
            </fo:table-body>
        </fo:table>

    </xsl:template>

    <xsl:template match="resultLogsList">
        <fo:table-row>
            <fo:table-cell  border="1px solid black">
                <fo:block  font-size="10pt">
                    <xsl:value-of select="TimeMoment"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell  border="1px solid black">
                <fo:block  font-size="10pt" >
                    <xsl:value-of select="FileName"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell  border="1px solid black" >
                <fo:block   font-size="10pt" >
                    <xsl:for-each select="Content">
                        <xsl:call-template name="split_value">
                            <xsl:with-param
                                    name="value"
                                    select="text()"
                            />
                            <xsl:with-param
                                    name="max_length"
                                    select="number(50)"
                            />
                        </xsl:call-template>
                    </xsl:for-each>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

    <xsl:template name="split_value">
        <xsl:param name="value"/>
        <xsl:param name="max_length"/>
        <xsl:variable name="ret">
            <xsl:value-of select="substring($value, 1, $max_length)"/>
            <xsl:if test="string-length($value) &gt; $max_length">
                <xsl:value-of select="'&#x200b;'"/>
                <xsl:call-template name="split_value">
                    <xsl:with-param
                            name="value"
                            select="substring($value, $max_length + 1)"
                    />
                    <xsl:with-param
                            name="max_length"
                            select="$max_length"
                    />
                </xsl:call-template>
            </xsl:if>
        </xsl:variable>
        <xsl:value-of select="$ret"/>
    </xsl:template>

    <xsl:template match="searchInfo">
        <fo:table width="100%" text-align="right" font-family="Arial">
            <fo:table-column column-width="58%"/>
            <fo:table-column column-width="42%"/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell border="none">
                        <fo:block>
                            Расположение логов:
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border="none">
                        <fo:block text-align="right">
                            <xsl:value-of select="location"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                    <fo:table-cell border="none">
                        <fo:block>
                            Регулярное выражение:
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border="none">
                        <fo:block>
                            <xsl:value-of select="regularExpression"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                    <fo:table-cell border="none">
                        <fo:block>
                            Временные промежутки:
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border="none">
                        <fo:block>
                            <xsl:apply-templates select="dateInterval"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <xsl:template match="dateInterval">
        <fo:table width="100%" text-align="right" font-family="Arial">
            <fo:table-column column-width="50%"/>
            <fo:table-column column-width="50%"/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell>
                        <fo:block>
                            Начало:
                            <xsl:value-of select="dateFromString"/>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                        <fo:block text-align="right">
                            Конец:
                            <xsl:value-of select="dateToString"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>

            </fo:table-body>
        </fo:table>
    </xsl:template>

</xsl:stylesheet>
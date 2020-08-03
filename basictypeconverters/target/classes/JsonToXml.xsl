<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output indent="yes"/>
    <xsl:strip-space elements="*"/>
    <xsl:param name="jsonText"></xsl:param>
    <xsl:mode on-no-match="shallow-copy"/>
    <xsl:template name="init">
        <xsl:apply-templates select="json-to-xml($jsonText)"/>
    </xsl:template>

<!--    <xsl:template match="string[@key = 'subjects']" xpath-default-namespace="http://www.w3.org/2005/xpath-functions">-->
    <xsl:template match="string[@key = '*']" xpath-default-namespace="http://www.w3.org/2005/xpath-functions">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <xsl:sequence select="parse-xml(.)/node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8"?>

<!--
Copyright (c) 2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

<xsl:stylesheet
  version="1.0"
  xmlns:docbook="http://docbook.org/ns/docbook"
  xmlns:xi="http://www.w3.org/2001/XInclude"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://docbook.org/ns/docbook">

  <!-- Generate XLM without indentation: -->
  <xsl:output method="xml" indent="yes"/>

  <!-- Copy the chapters and the appendix, and replace the rest of the
       document generated from the AsciiDoc with the typical structure
       used by Publican: -->
  <xsl:template match="/">

    <book>
      <xi:include href="Book_Info.xml"/>
      <xi:include href="Preface.xml"/>
      <xsl:copy-of select="/docbook:book/docbook:chapter"/>
      <xsl:copy-of select="/docbook:book/docbook:appendix"/>
      <xi:include href="Revision_History.xml"/>
      <index/>
    </book>

  </xsl:template>

</xsl:stylesheet>

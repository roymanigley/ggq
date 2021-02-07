<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes"></xsl:output>
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:param name="PROJECT_NAME" select="'code-with-quarkus'"/>
    <xsl:template match="/">
<html>
    <head>
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"></link>

        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <style>
            ul.list {
                padding-left: 3em !important;
            }
            ul.list > li {
                list-style-type: disclosure-closed !important;
            }
        </style>
    </head>
    <body>
        <nav class="teal">
            <div class="nav-wrapper teal container">
                <a href="#" class="brand-logo"><xsl:value-of select="$PROJECT_NAME" /></a>
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li>
                        <a href="/swagger-ui" target="_blank">Swagger Docs</a>
                    </li>
                    <li>
                        <a href="/openapi" target="_blank">Open API</a>
                    </li>
                </ul>
            </div>
        </nav>
        <main class="container">

           <h1>Welcome to <xsl:value-of select="$PROJECT_NAME" /></h1>
           <h4>This application was generated with <code>GGQ</code> a code generator for Quarkus.
           </h4>
            <br></br>
            <br></br>
            More information about Quarkus and <code>GGQ</code> here
            <ul class="list">
                <li>
                    <a href="https://github.com/roymanigley/ggq" target="_blank">GGQ - GitHub Repository</a>
                </li>
                <li>
                    <a href="https://quarkus.io/" target="_blank">quarkus.io</a>
                </li>
                <li>
                    <a href="https://quarkus.io/guides" target="_blank">quarkus.io - Guides</a>
                </li>
                <li>
                    <a href="https://code.quarkus.io/" target="_blank">quarkus.io - Generate application</a>
                </li>
                <li>
                    <a href="https://github.com/quarkusio/quarkus/issues" target="_blank">quarkus.io - Issue
                        Tracker
                    </a>
                </li>
            </ul>
        </main>
    </body>
</html>

    </xsl:template>
</xsl:stylesheet>
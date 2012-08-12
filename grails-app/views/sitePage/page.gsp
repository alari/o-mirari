<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${entry.title}</title>
    <link rel="alternate" title="ATOM" type="application/atom+xml" href="<site:atomFeedUrl for="${_site}"/>"/>
    <link rel="image_src" href="${entry.image.mediumSrc}" />
    <meta property="og:image" content="${entry.image.mediumSrc}"/>
</head>

<body>

<g:if test="${entry.title}">
    <mk:pageHeader>${entry.title}</mk:pageHeader>
</g:if>


<mk:withSmallSidebar>
    <mk:content>

        <g:each in="${entry.inners}" var="unit">
            <unit:renderPage for="${unit.viewModel}" only="${entry.inners.size() == 1}"/>
        </g:each>

    </mk:content>

    <mk:sidebar>
        <div style="text-align: center">
            <g:link for="${entry}"><img src="${entry.notInnerImage.smallSrc}"/></g:link>
        </div>

        <g:if test="${!entry.owner.isPortalSite()}">
            <div style="text-align: center">
                <follow:site/>
            </div>
        <div style="text-align: right">
            <b><g:link for="${entry.owner}">${entry.owner}</g:link></b>

            <br/>
            <i><mk:datetime date="${entry.publishedDate ?: entry.lastUpdated}"/></i>

        </div>

        </g:if>
            <rights:ifCanEdit unit="${entry}">
                <small>
                    <br/>
                    <g:link for="${entry}" action="edit">
                        Править эту страничку</g:link>
                </small>

            </rights:ifCanEdit>



    </mk:sidebar>
</mk:withSmallSidebar>

</body>
</html>
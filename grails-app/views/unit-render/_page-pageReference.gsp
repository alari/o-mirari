<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>

<unit:withPageReferenceUnit unit="${viewModel}">
    <div class="unit">
        
        <g:if test="${entry}">
            <div class="entry-announce">
                <span class="pull-left">
                    <g:link for="${entry}">
                        <img src="${entry.thumbSrc}"/></g:link>
                </span>

                <div>
                    <g:if test="${entry.title}">
                        <h3><g:link for="${entry}">${entry.title}</g:link></h3>
                    </g:if>

                    <i class="entry-announce-owner"><g:link for="${entry.owner}">${entry.owner}</g:link></i>

                    <small class="entry-announce-type"><g:message code="pageType.${entry.type.name}"/></small>
                </div>
            </div>
        </g:if>
        <g:else>
            <h3>Страничка недоступна</h3>
        </g:else>

    </div>
</unit:withPageReferenceUnit>

<r:require module="css_announcesGrid"/>
<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${entry.title}</title>
    <link rel="image_src" href="${entry.image.mediumSrc}" />
    <meta property="og:image" content="${entry.image.mediumSrc}"/>
</head>

<body>

<mk:withSmallSidebar>
    <mk:content>
        <article>
            <g:if test="${entry.title}">
                <mk:pageHeader>${entry.title}</mk:pageHeader>
            </g:if>

            <g:each in="${entry.inners}" var="unit">
                <unit:renderPage for="${unit.viewModel}" only="${entry.inners.size() == 1}"/>
            </g:each>

        </article>

        <hr/>
            <span class="pull-right">
            <script type="text/javascript" src="//yandex.st/share/share.js" charset="utf-8"></script>
            <div class="yashare-auto-init" data-yashareL10n="ru" data-yashareType="none" data-yashareQuickServices="vkontakte,facebook,twitter,lj,moikrug"></div>
            </span>
    </mk:content>
    <mk:sidebar>

        <div>
            <div style="text-align: center">
                <g:link for="${entry}"><img src="${entry.notInnerImage.thumbSrc}"/></g:link>
            </div>

            <div style="text-align: right">
                Автор: <b><g:link for="${entry.owner}">${entry.owner}</g:link></b>

                <br/>
                <em><g:message code="pageType.${entry.type.name}"/></em>
                <br/>
                <i><mk:datetime date="${entry.publishedDate ?: entry.lastUpdated}"/></i>
            </div>

            <div style="padding: 1em">
                <g:each in="${entry.tags}" var="t">
                    <g:link for="${t}" class="label" style="white-space: nowrap;"><i class="icon-tag"></i>${t}</g:link>
                </g:each>
            </div>
        <rights:ifCanEdit unit="${entry}">
            <div class="btn-group">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    Действия
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><g:link for="${entry}" action="setDraft" params="[draft: !entry.draft]"><g:message
                            code="unit.edit.setDraftTo.${entry.draft ? 'false' : 'true'}"/></g:link></li>

                    <li><g:link for="${entry}" action="edit">
                        <g:message code="unit.edit.button"/></g:link></li>

                    <rights:ifCanDelete unit="${entry}">
                        <li><g:link for="${entry}" action="delete" onclick="return confirm('Уверены?')">
                            <g:message code="unit.delete.button"/>
                        </g:link></li>
                    </rights:ifCanDelete>
                </ul>
            </div>
            </rights:ifCanEdit>
        </div>

    </mk:sidebar>
</mk:withSmallSidebar>

<r:require module="vm_comment"/>

<script type="text/javascript">
    var pageCommentsVM = {newText:""};
    $(function () {
        pageCommentsVM = new PageCommentsVM('${entry.url}', '${entry.owner.stringId}'<sec:ifLoggedIn>, '<site:profileId/>'<rights:ifCanComment entry="${entry}">, true</rights:ifCanComment></sec:ifLoggedIn>);
    });
</script>

<div data-bind="template:  { name: 'pageComments', data: pageCommentsVM }"></div>

<g:render template="/jquery-tmpl/comment"/>

</body>
</html>
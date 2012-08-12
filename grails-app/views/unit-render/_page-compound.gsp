<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>

<div class="unit">

    <g:if test="${!only && viewModel.title}">
        <g:if test="${viewModel.outerId}">
            <h3>${viewModel.title}</h3>
        </g:if>
        <g:else>
            <h2>${viewModel.title}</h2>
        </g:else>
    </g:if>

    <g:render template="/unit-render/compound/${viewModel.params.type}" model="[viewModel: viewModel]"/>

    <div class="unit-text">
        ${viewModel.params.html}
    </div>

    <div class="unit-credits">
        <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
        <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
    </div>

</div>
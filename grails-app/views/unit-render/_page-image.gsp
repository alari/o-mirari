<%--
 * @author alari
 * @since 11/26/11 1:03 AM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<div class="unit-image unit">
    <div class="unit-image-pic">
        <img src="${viewModel.image.standardSrc}"/>

        <g:if test="${viewModel.title}">
            <br/><em>${viewModel.title}</em>
        </g:if>
    </div>


    <div class="unit-credits">
        <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
        <a href="${viewModel.image.maxSrc}">Посмотреть в максимальном размере</a>
        <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
    </div>

    <g:render template="/unit-render/inners" model="[unit: viewModel]"/>
</div>
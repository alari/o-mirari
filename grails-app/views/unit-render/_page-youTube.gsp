<%--
  By alari
  Since 1/5/12 12:23 AM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<div class="unit-image unit">
    <div class="unit-image-pic">
        <iframe width="420" height="315" src="http://www.youtube.com/embed/${viewModel.params.externalId}"
                frameborder="0" allowfullscreen></iframe>
    </div>

    <div class="unit-credits">
        <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
        <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
    </div>

    <g:render template="/unit-render/inners" model="[unit: viewModel]"/>
</div>
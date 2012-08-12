<%--
  By alari
  Since 1/5/12 12:34 AM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<div class="unit-image unit">
    <div class="unit-image-pic">
        <embed name="playerblog${viewModel.id}" data-bind="uniqueName: true"
               src="http://www.russia.ru/player/main.swf?103"
               flashvars="name=${viewModel.params.externalId}&from=blog&blog=true" width="448" height="252"
               bgcolor="#000000" allowScriptAccess="always" allowFullScreen="true"></embed>
    </div>

    <div class="unit-credits">
        <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
        <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
    </div>

    <g:render template="/unit-render/inners" model="[unit: viewModel]"/>
</div>
<%--
  By alari
  Since 12/21/11 9:35 PM
--%>
<% // TODO: remove viewModel usage from template!   %>
<%@ entry contentType="text/html;charset=UTF-8" %>
<div class="unit-audio unit">
    <div class="unit-audio-player">
        <audio data-bind="audio: '/js/vendor/mediaelement/'"
               src="${viewModel.params.mpeg}"></audio>

        <g:if test="${viewModel.title}">
            <br/><em>${viewModel.title}</em>
        </g:if>
    </div>

    <div class="unit-credits">
        <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
        <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
    </div>

    <g:render template="/unit-render/inners" model="[unit: viewModel]"/>
</div>
<r:require module="vendor_mediaelement"/>

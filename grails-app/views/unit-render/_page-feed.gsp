<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>

<unit:withFeedUnit unit="${viewModel}">
    <div class="unit" data-bind="feedUnit: {url:'${viewModel.params.url}', drafts:'${viewModel.params.drafts}', style: '${viewModel.params.style}', last: '${viewModel.params.last}'}, template: {name: 'feedUnit', data: this}">
        <g:if test="${viewModel.title}">
            <g:if test="${viewModel.outerId}">
                <h3>${viewModel.title}</h3>
            </g:if>
            <g:else>
                <h2>${viewModel.title}</h2>
            </g:else>
        </g:if>

        <unit:renderFeed feedParams="${feedParams}" unit="${viewModel}"/>

        <div class="unit-credits">
            <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
            <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
        </div>

    </div>
</unit:withFeedUnit>

<mk:tmpl id="feedUnit">
    <div data-bind="visible: draftsCount, template: {name:'announces_drafts'}">
    </div>
    <div data-bind="if: last">
        <div data-bind="template: {name:lastTemplate,data:last}"></div>
    </div>
    <div data-bind="template: {name:pagesTemplate}"></div>
    <div data-bind="if:hasMorePages" align="center"><h6><a href="#" data-bind="click:loadPage">Ещё страницы</a></h6></div>
</mk:tmpl>

<r:require module="ko_feedUnit"/>
<g:render template="/jquery-tmpl/announce/announces"/>
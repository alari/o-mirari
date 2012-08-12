<mk:tmpl id="digest">
    <div data-bind="template: { name: 'notice', foreach: notices }"></div>
    
    <div data-bind="if:hasMorePages" style="text-align: center">
        <h6><a href="#" data-bind="click: load">Загрузить ещё</a></h6>
    </div>
</mk:tmpl>

<style type="text/css">
    .noticeNew {
        background-color: rgba(255, 155, 90, 0.1);
    }
    .noticeWatched {
        background-color: rgba(158, 204, 255, 0.15);
    }
    .notice {
        margin-bottom: 1em;
        border-radius: 2px;
    }
</style>

<mk:tmpl id="notice">
    <div class="pull-left" data-bind="click:toggle" style="cursor: pointer; width: 5em; text-align: center;">
        <i class="icon-arrow-down"></i>
    </div>
    <div class="notice" data-bind="template: {name: reason.tmpl, data:reason}, event:{mouseover: watch}, css: {noticeNew: !watched(), noticeWatched: watched}"></div>
</mk:tmpl>
<mk:tmpl id="reason_page_comment">
    <h6 data-bind="click: notice.toggle">
        <span data-bind="template:{name:'site_link',data:comment.owner}"></span>:
    Комментарий на страницу:
        <a data-bind="attr:{href:notice.entry.url}, text: notice.entry.title ? notice.entry.title : '* * *'"></a>
    </h6>
    <div data-bind="visible: notice.visible, template: {name:'comment', data:comment}"></div>
</mk:tmpl>

<mk:tmpl id="reason_comment_reply">
    <h6 data-bind="click: notice.toggle">
        <span data-bind="template:{name:'site_link',data:reply.owner}"></span>:
    Ответ на комментарий на странице:
        <a data-bind="attr:{href:notice.entry.url}, text: notice.entry.title ? notice.entry.title : '* * *'"></a>
    </h6>
    <div data-bind="visible: notice.visible, template: {name:'comment', data:comment}"></div>
</mk:tmpl>

<mk:tmpl id="reason_page_reply">
    <h6 data-bind="click: notice.toggle">
        <span data-bind="template:{name:'site_link',data:reply.owner}"></span>:
    Ответ на странице:
        <a data-bind="attr:{href:notice.entry.url}, text: notice.entry.title ? notice.entry.title : '* * *'"></a>
    </h6>
    <div data-bind="visible: notice.visible, template: {name:'comment', data:comment}"></div>
</mk:tmpl>

<mk:tmpl id="reason_follow_page">
    <h6>
        <span data-bind="template:{name:'site_link',data:notice.entry.owner}"></span>:
    
        <i class="icon-plus-sign"></i> <span data-bind="text: notice.entry.type"></span>:
        <a data-bind="attr:{href:notice.entry.url},text: notice.entry.title ? notice.entry.title : '* * *'"></a>


    </h6>
</mk:tmpl>

<mk:tmpl id="reason_follower_new">
    <h6>
        Теперь за Вами следует: <span data-bind="template:{name:'site_link',data:actor}"></span>
    </h6>
</mk:tmpl>

<g:render template="/jquery-tmpl/comment"/>
<g:render template="/jquery-tmpl/site"/>
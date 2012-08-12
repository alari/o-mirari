<g:render template="/jquery-tmpl/announce/small"/>
<g:render template="/jquery-tmpl/announce/wide"/>
<g:render template="/jquery-tmpl/announce/thumbnails"/>
<g:render template="/jquery-tmpl/announce/links"/>
<g:render template="/jquery-tmpl/announce/blog"/>
<g:render template="/jquery-tmpl/announce/full"/>

<r:require module="css_announcesGrid"/>

<g:render template="/jquery-tmpl/site"/>

<mk:tmpl id="announces_drafts">
    <h6 data-bind="click: toggleDrafts" style="cursor: pointer">Черновики: <span data-bind="text: draftsCount"></span></h6>
    <div class="entry-links" data-bind="visible: draftsVisible, template:{name:'announce_link',foreach:drafts}"></div>
</mk:tmpl>
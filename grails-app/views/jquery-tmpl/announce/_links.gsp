<mk:tmpl id="announce_link">
    <span class="entry-link">
            <a data-bind="attr:{href:url}, text:title ? title : '* * *'"></a>

        &ndash; <i data-bind="template:{name:'site_link', data:owner}"></i>

        <small data-bind="text:typeString"></small>
    </span>
</mk:tmpl>

<mk:tmpl id="announces_links">
    <div class="entry-links" data-bind="template:{name:'announce_link',foreach:pages}"></div>
</mk:tmpl>
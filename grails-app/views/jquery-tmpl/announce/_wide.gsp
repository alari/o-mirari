<mk:tmpl id="announce_wide">
    <div class="entry-announce">
        <span class="pull-left">
            <a data-bind="attr:{href:url}"><img data-bind="attr:{src:image.thumbSrc}"/></a>
        </span>

        <div>
            <div data-bind="if:title">
                <h3><a data-bind="attr:{href:url}, text:title"></a></h3>
            </div>

            <i class="entry-announce-owner" data-bind="template:{name:'site_link', data:owner}"></i>

            <small class="entry-announce-type" data-bind="text:typeString"></small>
        </div>
    </div>
</mk:tmpl>

<mk:tmpl id="announces_wide">
    <div class="row" data-bind="template: {name:'announce_wide',foreach:pages}"></div>
</mk:tmpl>
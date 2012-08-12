<mk:tmpl id="announce_small">
        <div class="span5">
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
        </div>
</mk:tmpl>

<mk:tmpl id="announces_small">
    <div class="row" data-bind="template: {name:'announce_small',foreach:pages}"></div>
</mk:tmpl>
<mk:tmpl id="announce_thumbnail">
    <li class="span2">
        <div class="thumbnail" style="text-align: center">
            <a data-bind="attr:{href:url}"><img data-bind="attr:{src:image.smallSrc}"/></a>

            <div data-bind="if:title">
                <h3><a data-bind="attr:{href:url}, text:title"></a></h3>
            </div>

            <i class="entry-announce-owner" data-bind="template:{name:'site_link', data:owner}"></i>
        </div>
    </li>
</mk:tmpl>

<mk:tmpl id="announces_thumbnails">
    <ul class="thumbnails" data-bind="template:{name:'announce_thumbnail',foreach:pages}"></ul>
</mk:tmpl>
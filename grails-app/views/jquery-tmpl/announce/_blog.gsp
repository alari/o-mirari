<mk:tmpl id="announce_blog">

    <article class="feed-entry">
        <div data-bind="if:title">
            <h2><a data-bind="attr:{href:url}, text:title"></a></h2>
        </div>


        <div class="pull-right" style="text-align: center">

            <!-- TODO: notInnerImage -->
            <a data-bind="attr:{href:url}"><img data-bind="attr:{src:image.thumbSrc}"/></a>

            <div style="text-align: right">
                Автор: <b data-bind="template:{name:'site_link', data:owner}"></b>

                    <br/>
                    <em  data-bind="text:typeString"></em>

                <br/>
                <i data-bind="timestamp:date"></i>
            </div>
        </div>


        <div data-bind="html:html"></div>

        <div class="entry-credits">
        <a data-bind="attr:{href:url}, timestamp:date"></a>

        <a data-bind="attr:{href:url}, text: title ? title : '* * *'"></a>
        </div>
        <br clear="all"/>
    </article>
</mk:tmpl>

<mk:tmpl id="announces_blog">
    <div class="pages-feed" data-bind="template: {name:'announce_blog',foreach:pages}"></div>
</mk:tmpl>

<r:require module="ko_timestamp"/>

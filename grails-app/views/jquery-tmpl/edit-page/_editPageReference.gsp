<%--
 * @author alari
 * @since 23.02.12
--%>


<%@ entry contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_pageReference">
    <div data-bind="ifnot: params.url">
        <h4>Страница удалена владельцем</h4>
    </div>
    <div data-bind="if: params.url">
        <div class="entry-announce">
            <span class="pull-left">
                <img data-bind="attr:{src:params.thumbSrc}"/>
            </span>

            <div>
                <div data-bind="if: params.title">
                    <h3 data-bind="text:params.title"></h3>
                </div>

                <i class="entry-announce-owner" data-bind="text:params.ownerName"></i>

            </div>
        </div>
    </div>
</mk:tmpl>

<r:require module="css_announcesGrid"/>
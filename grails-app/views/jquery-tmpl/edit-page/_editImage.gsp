<%--
 * @author alari
 * @since 11/14/11 3:28 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_image">

    <div class="unit-image-pic">
        <img data-bind="attr: {src: image.standardSrc}"/>
        <br/>
        <input type="text" data-bind="value: title" placeholder="Заголовок / подпись к картинке"/>
    </div>
</mk:tmpl>
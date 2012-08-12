<%--
 * @author alari
 * @since 11/22/11 9:25 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_renderInners">
    <div class="unit-title">
        <input type="text" data-bind="value: title" placeholder="Заголовок блока" maxlength="128"/>
    </div>
    <p>
        Вы можете помещать другие элементы внутрь этого, чтобы применить к ним какой-то особый способ оформления.
    </p>
    <div align="right">
        Оформить внутренние как: <select data-bind="value: params.renderStyle"><option
            value="carousel">Карусель</option></select>
    </div>

</mk:tmpl>
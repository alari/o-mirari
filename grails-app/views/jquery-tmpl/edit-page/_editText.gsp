<%--
 * @author alari
 * @since 11/22/11 9:25 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_text">
    <div class="unit-title">
        <input type="text" data-bind="value: title" placeholder="Заголовок текста" maxlength="128"/>
    </div>

    <span class="float-left-box cursor-link"
          data-bind="click: toggleContentVisibility, text: contentVisible() ? '-' : '+'">
    </span>

    <div data-bind="visible: contentVisible">
        <textarea class="unit-text"
                  data-bind="value: params.text, autoResize: true, valueUpdate: 'afterkeydown'"></textarea>
    </div>
</mk:tmpl>

<r:require module="ko_autoResize"/>
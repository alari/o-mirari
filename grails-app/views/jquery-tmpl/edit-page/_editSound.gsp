<%--
  By alari
  Since 12/15/11 6:54 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_sound">
    <input type="text" data-bind="value: title" placeholder="Заголовок / подпись к звуку (placeholder)"
           maxlength="128"/>
    <audio data-bind="audio: '/js/mediaelement/'" data-bind="attr: {src: params.mpeg}""></audio>
</mk:tmpl>

<r:require module="vendor_mediaelement"/>
<%--
 * @author Dmitry Kurinskiy
 * @since 22.10.11 18:21  
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<g:each in="${alerts}" var="alert">
    <div data-alert="alert" class="alert alert-${alert.level}">
        <a class="close" href="#" data-dismiss="alert">&times;</a>

        <p>${message(code: alert.code, args: alert.params)}</p></div>
</g:each>
<%--
  @author Dmitry Kurinskiy
  @since 29.08.11 14:47
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
Здравствуйте, ${username},<br/>
<br/>
Если Вы хотите изменить Ваш основной ящик электропочты на этот, пожалуйста, пройдите по
<g:link base="http://${host}" controller="settings" action="applyEmailChange"
        params="[t: token]">ссылке</g:link>.

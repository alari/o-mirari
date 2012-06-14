<%--
  @author Dmitry Kurinskiy
  @since 29.08.11 14:47
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
Здравствуйте, ${username},<br/>
<br/>
Вы (или другой претендент на это звание) запросили возможность сменить пароль.<br/>
<br/>
Если это были не Вы, просто проигнорируйте это письмо; никакие изменения сделаны не были.<br/>
<br/>
Если же это были Вы, перейдите по <g:link base="http://${host}" controller="register" action="resetPassword"
                                          params="[t: token]">этой ссылке</g:link>, чтобы изменить Ваш пароль.

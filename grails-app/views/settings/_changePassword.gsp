<%--
 * @author Dmitry Kurinskiy
 * @since 22.10.11 18:55  
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<g:formRemote name="changePwdForm" update="changePwdForm" url="[controller: 'settings', action: 'changePassword']" method="post"
              class="form-horizontal">
    <fieldset>
        <legend>${message(code: "personPreferences.changePassword.title")}</legend>
        <mk:formLine labelCode="personPreferences.changePassword.oldPassword" bean="${chPwdCommand}"
                     field="oldPassword">
            <g:passwordField name="oldPassword"/>
        </mk:formLine>
        <mk:formLine labelCode="personPreferences.changePassword.password" bean="${chPwdCommand}" field="password">
            <g:passwordField name="password"/>
        </mk:formLine>
        <mk:formLine labelCode="personPreferences.changePassword.password2" bean="${chPwdCommand}" field="password2">
            <g:passwordField name="password2"/>
        </mk:formLine>
        <mk:formActions>
            <g:submitButton name="sbm" class="btn btn-info"
                            value="${message(code: 'personPreferences.changePassword.submit')}"/>
        </mk:formActions>
    </fieldset>
    <input type="hidden" name="email" value="${sec.username()}"/>
</g:formRemote>
<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>
<head>
    <title><g:message code='register.login.title'/></title>
    <meta name='layout' content='main'/>
</head>

<body>

<form action='${postUrl}' method='POST' class="form-horizontal" id="loginForm" name="loginForm" autocomplete='off'>
    <fieldset>
        <legend><g:message code='register.login.signin'/></legend>

        <mk:formLine labelCode="register.login.username" field="username">
            <input name="${SpringSecurityUtils.getSecurityConfig().apf.usernameParameter}" id="username" size="20"
                   value="${session.getAttribute('SPRING_SECURITY_LAST_USERNAME')}"/>
        </mk:formLine>
        <mk:formLine labelCode="register.login.password" field="password">
            <input type="password" name="${SpringSecurityUtils.getSecurityConfig().apf.passwordParameter}" id="password"
                   size="20"/>
        </mk:formLine>

        <div class="control-group form-inline">
            <input type="checkbox" class="checkbox" name="${rememberMeParameter}" id="remember_me" checked="checked"/>
            <label for='remember_me'><g:message code='register.login.rememberme'/></label> |
            <span class="forgot-link">
                <g:link controller='register' action='forgotPassword'><g:message
                        code='register.login.forgotPassword'/></g:link>
            </span>
        </div>
    </fieldset>

    <mk:formActions>
        <input type="submit" value="${message(code: 'register.login.login')}" class="btn btn-primary"/>
        <g:link controller="register" class="btn btn-info">${message(code: 'register.login.register')}</g:link>
    </mk:formActions>
</form>

<script>
    $(document).ready(function () {
        $('#username').focus();
    });
</script>

</body>

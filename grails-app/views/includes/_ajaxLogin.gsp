<%@ entry import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>
<style>
input.login {
    display: block;
}
</style>

<div class='s2ui_center'>

    <div id="loginFormContainer" style='display:none' title="${message(code: 'register.login.signin')}">
        <g:form controller='${SpringSecurityUtils.getSecurityConfig().apf.filterProcessesUrl}' name="loginForm"
                autocomplete='off'>

            <label for="username"><g:message code='register.login.username'/></label>
            <input class='login' name="${SpringSecurityUtils.getSecurityConfig().apf.usernameParameter}" id="username"
                   size="20"/>

            <label for="password"><g:message code='register.login.password'/></label>
            <input class='login' type="password" name="${SpringSecurityUtils.getSecurityConfig().apf.passwordParameter}"
                   id="password" size="20"/>

            <input type="checkbox" class="checkbox"
                   name="${SpringSecurityUtils.getSecurityConfig().rememberMe.parameter}" id="remember_me"
                   checked="checked"/>
            <label for='remember_me'><g:message code='register.login.rememberme'/></label> |

            <g:link controller='personRegister' action='forgotPassword'><g:message
                    code='register.login.forgotPassword'/></g:link>
            <g:link controller='personRegister'><g:message code='register.login.register'/></g:link>

            <input type='submit' class='s2ui_hidden_button'/>

        </g:form>
        <div id='loginMessage' style='color: red; margin-top: 10px;'></div>
    </div>
</div>

<script>
    var loginButtonCaption = "<g:message code='register.login.login'/>";
    var logoutLink = '<%=link(controller: 'logout') { 'Logout' }%>';
    var loggingYouIn = "<g:message code='register.login.loggingYouIn'/>";
</script>
<g:javascript src='jquery/jquery.form.js'/>
<g:javascript src='ajaxLogin.js'/>


<head>
    <meta name='layout' content='main'/>
    <title><g:message code='register.title'/></title>
</head>

<body>

<mk:pageHeader>${message(code: 'register.title')}</mk:pageHeader>

<g:form name='registerForm' method="post" controller="register" action="index" class="form-horizontal">

    <g:if test='${emailSent}'>
        <div class="alert alert-success">
            ${message(code: 'register.confirm.sent')}
            <test:echo><g:link class="test verify-registration" controller="register" action="verifyRegistration"
                               params="[t: token]">TEST:confirm</g:link></test:echo>
        </div>
    </g:if>
    <g:else>

        <fieldset>
            <legend>Аккаунт</legend>

            <mk:formLine labelCode="person.email.label" bean="${command}" field="email">
                <g:textField class="medium" type="email" size="16" name="email" bean="${command}"/>
            </mk:formLine>

            <mk:formLine labelCode="person.password.label" bean="${command}" field="password">
                <g:passwordField class="medium" size="16" name="password" bean="${command}"/>
            </mk:formLine>

            <mk:formLine labelCode="person.password2.label" bean="${command}" field="password">
                <g:passwordField class="medium" size="16" name="password2" bean="${command}"/>
            </mk:formLine>

        </fieldset>

        <fieldset><legend>Основной профиль</legend>

            <mk:formLine labelCode="profile.name.label" bean="${command}" field="name">
                <g:textField class="medium" type="text" size="16" name="name" bean="${command}"/>
            </mk:formLine>

            <mk:formLine labelCode="profile.displayName.label" bean="${command}" field="displayName">
                <g:textField class="medium" type="text" size="16" name="displayName" bean="${command}"/>
            </mk:formLine>
        </fieldset>

        <mk:formActions>
            <g:submitButton class="btn btn-primary" name="submit"
                            value="${message(code: 'register.submit')}"/>
        </mk:formActions>

    </g:else>

</g:form>

<script>
    $(document).ready(function () {
        $('#email').focus();
    });
</script>

</body>

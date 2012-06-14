<head>
    <title><g:message code='register.forgotPassword.title'/></title>
    <meta name='layout' content='main'/>
</head>

<body>

<mk:pageHeader>${message(code: 'register.forgotPassword.header')}</mk:pageHeader>

<g:if test='${emailSent}'>
    <div class="alert alert-success">
        <g:message code='register.forgotPassword.sent'/>
        <test:echo><g:link controller="register" action="resetPassword" params="[t: token]"
                           class="test reset-pwd">TEST:reset-pwd</g:link></test:echo>
    </div>
</g:if>

<g:else>

    <g:form controller="register" action='forgotPassword' name="forgotPasswordForm" autocomplete='off' class="well">

        <h4><g:message code='register.forgotPassword.description'/></h4>

        <mk:formLine labelCode="register.forgotPassword.username" field="name">
            <g:textField name="name" size="25"/>
        </mk:formLine>

        <mk:formActions>
            <g:submitButton class="btn btn-primary" name="submit"
                            value="${message(code: 'register.forgotPassword.submit')}"/>
        </mk:formActions>

    </g:form>

    <script>
        $(document).ready(function () {
            $('#username').focus();
        });
    </script>
</g:else>

</body>

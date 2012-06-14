<%--
  By alari
  Since 5/12/12 10:48 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='main'/>
  <title>Настройки аккаунта</title>
</head>
<body>
        <h1><sec:username/></h1>

<g:render template="changePassword"/>

<g:formRemote update="changeEmailUpdate" name="changeEmail" url="[controller: 'settings',action: 'changeEmail']"
              method="post" class="form-horizontal">
    <fieldset>
        <legend>${message(code: "personPreferences.changeEmail.title")}</legend>

        <mk:formLine labelCode="personPreferences.changeEmail.current">
            <span class="uneditable-input">${account.email}</span>
        </mk:formLine>
        <div id="changeEmailUpdate"></div>
        <mk:formLine labelCode="personPreferences.changeEmail.field">
            <g:textField name="email"/> <g:submitButton name="submit" class="btn btn-info"
                                                        value="${message(code: 'personPreferences.changeEmail.submit')}"/>
        </mk:formLine>

    </fieldset>
</g:formRemote>
</body>
</html>
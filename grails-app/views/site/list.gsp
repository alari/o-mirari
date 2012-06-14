<%--
  By alari
  Since 5/15/12 10:39 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
  <title>Ваши сайты</title>
</head>
<body>
<mk:pageHeader>
    Ваши сайты
</mk:pageHeader>

<g:each in="${sites}" var="s">
    <div class="well">
        ${s.name}
        ${s.displayName}
    </div>
</g:each>
</body>
</html>
<%--
  By alari
  Since 8/4/12 10:53 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
  <title>Create Entry By Title</title>
</head>
<body>

<g:form method="post" action="createEntry">
<f:all bean="entry"/>

    <f:field property="piles"/>
    <g:submitButton name="submit"/>
</g:form>

</body>
</html>
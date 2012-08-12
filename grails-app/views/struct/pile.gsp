<%--
  By alari
  Since 8/4/12 11:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta content="main" name="layout"/>
  <title>Entries in pile</title>
</head>
<body>

<mk:pageHeader>${pile.title}</mk:pageHeader>

<g:each in="${entries}" var="entry">
    <g:render template="entryInPile" bean="${entry}"/>
</g:each>

${json}

</body>
</html>
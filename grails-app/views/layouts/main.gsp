<%--
  @author Dmitry Kurinskiy
  @since 09.09.11 10:42
--%>

<%@ entry import="grails.util.Environment" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <title><g:layoutTitle default="${message(code: 'layout.title')}"/></title>
    <link rel="SHORTCUT ICON" href="http://ya.ru/favicon.ico"/>
    <g:layoutHead/>
    <r:require modules="jquery,bootstrap,app_alerts"/>
    <r:layoutResources/>
</head>

<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="/">mirari</a>

            <ul class="nav pull-right <sec:ifLoggedIn>logged-in</sec:ifLoggedIn>">

                <sec:ifLoggedIn>
                    <li><g:link controller="struct" action="createEntry">${message(code: "layout.createEntry")}</g:link>
                    <li><g:link controller="settings"><sec:username/></g:link>
                    </li>
                    <li><g:link controller="logout">${message(code: "layout.logout")}</g:link></li>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li><g:link controller="register">${message(code: "layout.register")}</g:link></li>
                    <li><g:link controller="login">${message(code: "layout.login")}</g:link></li>
                </sec:ifNotLoggedIn>

            </ul>
        </div>
    </div>
</div>

<div class="container">
    <div class="alerts-container" data-bind="template: { name: 'alerts', foreach: alertsVM.alerts }"></div>

    <g:layoutBody/>

    <footer class="footer">
        <div class="container">
            <div class="row">

                <div class="span3">
                    ${message(code: "layout.footer.copyright")}
                </div>

                <div class="span6">
                    <test:echo><span
                            id="test-entry">${webRequest.controllerName}:${webRequest.actionName}</span>
                        <em>${request.getHeader("Host")}</em>
                        <br/></test:echo>
                    <em>${System.currentTimeMillis() - (startTime?:0)} &mu;</em>
                </div>

                <div class="span3">
                    <em>${message(code: "layout.footer.version", args: [g.meta(name: "app.version")])}</em>
                </div>
            </div>
        </div>
    </footer>

</div>


<r:layoutResources/>

<script type="text/javascript">
    $(function () {
        <g:alerts/>
        ko.applyBindings();
    });
</script>

<mk:tmpl id="alerts">
    <div data-bind="attr: {class: 'alert alert-'+level}, fadeOut: {delay: '4', after: remove}">
        <a class="close" href="#" data-bind="click:remove">&times;</a>

        <p data-bind="html:message"></p></div>
</mk:tmpl>

<r:layoutResources disposition="bottom"/>

</body>
</html>
<%--
 * @author alari
 * @since 10/27/11 10:00 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title><g:message code="unit.add.title" args="[(_portal ?: _site).toString()]"/></title>

</head>

<body>

<script type="text/javascript">
    var pageEditVM;

    $().ready(function () {
        pageEditVM = new PageVM();
        pageEditVM.type("${type.name}");
        pageEditVM.avatar.thumbSrc = "${thumbSrc}";
    });
</script>

<r:script disposition="bottom">
    $().ready(function () {
        pageEditVM.innersAct.addCompoundUnit("${type.name}")
    });
</r:script>

<div id="unit" data-bind="template: { name: 'pageEdit', data: pageEditVM }">
    LOADING
</div>

<r:require module="mirariUnitAdd"/>

<g:render template="/jquery-tmpl/edit-entry/edit"/>
</body>
</html>
<%--
  By alari
  Since 12/14/11 5:36 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${entry.title}</title>
</head>

<body>

<script type="text/javascript">
    var pageEditVM;

    $().ready(function () {
        pageEditVM = new PageVM();
        jsonGetReact('<site:url for="${entry}" action="viewModel"/>', function (mdl) {
            pageEditVM.fromJson(mdl.page);
        });
    });
</script>

<div id="unit" data-bind="template: { name: 'pageEdit', data: pageEditVM }">
    <h5>Подождите, идёт загрузка...</h5>
</div>

<r:require module="mirariUnitAdd"/>

<g:render template="/jquery-tmpl/edit-entry/edit"/>

</body>
</html>
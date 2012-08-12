<%--
  By alari
  Since 1/4/12 11:36 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_youTube">
    <center><iframe width="420" height="315"
                    data-bind="uniqueName: true, attr: {src: 'http://www.youtube.com/embed/'+params.externalId }"
                    frameborder="0" allowfullscreen></iframe></center>
</mk:tmpl>

<mk:tmpl id="edit_russiaRu">
    <center><embed src="http://www.russia.ru/player/main.swf?103"
                   data-bind="uniqueName: true, attr: {flashvars: 'name='+params.externalId+'&from=blog&blog=true', name: params.externalId}"
                   width="448" height="252" bgcolor="#000000" allowScriptAccess="always" allowFullScreen="true"></embed>
    </center>
</mk:tmpl>
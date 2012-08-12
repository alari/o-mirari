<%--
  By alari
  Since 12/15/11 3:33 PM
--%>

<%@ entry contentType="text/html;charset=UTF-8" %>
<g:if test="${unit.inners.size() > 0}">
    <div class="unit-inners">
        <g:each in="${unit.inners}" var="u">
            <unit:renderPage for="${u}" only=""/>
        </g:each>
    </div></g:if>
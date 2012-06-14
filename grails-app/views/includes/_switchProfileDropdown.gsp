<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <g:each in="${sites}" var="s">
            <li>
                <site:link for="${s}" controller="sitePreferences" action="makeMain" title="Переключиться"/>
            </li>
        </g:each>
    </ul>
</li>
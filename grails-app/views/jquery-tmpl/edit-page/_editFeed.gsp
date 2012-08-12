<%--
 * @author alari
 * @since 23.02.12
--%>


<%@ entry import="mirari.model.entry.PageType" contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_feed">
    <div class="unit-title">
        <input type="text" data-bind="value: title" maxlength="128" placeholder="Название блока"/>
    </div>

    <p><small>Этот блок будет автоматически составлен из страничек, опубликованных на сайте. Вы можете указать источник этих страничек и способ их отображения.</small></p>

    <div class="row">
        <div class="span3 offset1">
            <h6>Источник страниц:</h6>

            <select data-bind="value: params.source, disable: params.locked" class="span2">
                <option value="all">Все страницы</option>
                <g:each in="${PageType.baseValues()}" var="type">
                    <option value="${type.name}"><g:message code="pageType.s.${type.name}"/></option>
                </g:each>
                <option value="tag">Тег</option>
            </select>
        </div>

        <div class="span2">
            <h6>Выводить штук:</h6>

            <select data-bind="value: params.num" class="span1">
                <g:each in="${1..24}" var="n">
                    <option value="${n}">${n}</option>
                </g:each>
            </select>
        </div>

        <div class="span3">
            <h6>Отображение:</h6>

            <select data-bind="value: params.style">
                <option value="links">Список маленьких ссылок</option>
                <option value="thumbnails">Маленькие картинки</option>
                <option value="small">Анонсы, по два на строку</option>
                <option value="wide">Анонсы, по подному на строку</option>
                <option value="blog">Блог (1-й фрагмент полностью)</option>
                <option value="full">Страницы полностью</option>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="span5 offset1">
            <p>
                <small>Вы можете по-особенному оформить последнюю страничку потока, чтобы обратить особое внимание на обновления.</small>
            </p>
            <p>
                <small>
                    <a href="#" data-bind="click: pageEditVM.innersAct.addFeedUnit, clickScroll: '#editBottom'">Добавить другой поток</a>
                </small>
            </p>
        </div>
        <div class="span3">
            <h6>Отображение последней странички</h6>
            <select data-bind="value: params.last">
                <option value="none">Без особенного стиля</option>
                <option value="wide">Анонс шириной в строку</option>
                <option value="blog">Блог (1-й фрагмент полностью)</option>
                <option value="full">Страница полностью</option>
            </select>
        </div>
    </div>
</mk:tmpl>
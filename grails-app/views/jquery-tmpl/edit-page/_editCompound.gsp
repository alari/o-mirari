<mk:tmpl id="edit_compound">
    <div class="unit-title">
        <input type="text" data-bind="value: title" placeholder="Заголовок или подзаголовок" maxlength="128"/>
    </div>

    <div data-bind="compound: $data, template: {name: 'edit_compound_'+params.type}">

    </div>
</mk:tmpl>

<mk:tmpl id="edit_compound_poetry">
    <h4>it's a poetry</h4>
    <ul>
        <li>upload an image</li>
        <li>upload a reading</li>
        <li>upload a song</li>
    </ul>
    <h1 data-bind="text: text"></h1>
    <textarea class="unit-text"
              data-bind="value: text, autoResize: {minHeight: 240}, valueUpdate: 'afterkeydown'"></textarea>
</mk:tmpl>

<r:require modules="ko_compound,ko_autoResize"/>
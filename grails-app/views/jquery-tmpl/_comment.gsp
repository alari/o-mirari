<%--
  By alari
  Since 2/2/12 2:52 PM
--%>

<r:require modules="ko_autoResize,ko_ctrlEnter"/>
<r:require module="vm_pageComments"/>


<mk:tmpl id="pageComments">
    <div data-bind="if: comments().length || canPostComment">
        <div class="well">
            <h4>Комментарии:</h4>
        </div>
    </div>

    <div data-bind="template: { name: 'comment', foreach: comments }"></div>

    <div data-bind="if: canPostComment">
        <form class="well">
            <div>
                <input type="text" class="span7" data-bind="value: newComment.title"
                       placeholder="Заголовок комментария (не обязательно)" maxlength="128"/>
            </div>

            <div>
                <textarea class="span9"
                          data-bind="autoResize: {maxHeight: 2000}, valueUpdate: 'afterkeydown', value: pageCommentsVM.newComment.text, ctrlEnter: pageCommentsVM.newComment.post"></textarea>
            </div>
            <mk:formActions>
                <button class="btn" data-bind="click: newComment.post">Сохранить комментарий</button>
            </mk:formActions>
        </form>
    </div>
</mk:tmpl>

<mk:tmpl id="comment">
    <!-- remove X -->
    <span class="pull-right close" data-bind="if: canRemove(), click: remove">&times;</span>
    <!-- comment itself -->
    <div class="row">
        <div class="span2" style="text-align: center">
            <span data-bind="template:{name:'site_link', data:owner}"></span>
            <br/>
            <img data-bind="attr: {src: owner.avatar.smallSrc}"/>
        </div>

        <div class="span10">
            <div data-bind="if: title"><h3 data-bind="text: title"></h3></div>

            <div data-bind="html: html"></div>
            <span class="pull-right" data-bind="timestamp: dateCreated"></span>
        </div>
    </div>
    <!-- replies -->
    <div class="row">
        <div class="span10 offset2">
            <div data-bind="template: { name: 'reply', foreach: replies }"></div>
            <!-- Post reply -->
            <div class="row" data-bind="if: canPostReply">
                <div class="offset3 span5">
                    <textarea class="span5"
                              data-bind="autoResize: {minHeight: 10, extraSpace: 5}, valueUpdate: 'afterkeydown', value: newReply.text, ctrlEnter: newReply.post"></textarea>
                </div>

                <div class="span2">
                    <button class="btn" data-bind="click: newReply.post">Ответить</button>
                </div>
            </div>
        </div>
    </div>

    <hr/>
</mk:tmpl>

<mk:tmpl id="reply">
    <!-- remove X -->
    <span class="pull-right close" data-bind="if: canRemove(), click: remove">&times;</span>
    <!-- reply itself -->
    <div class="row">
        <div class="span2" style="text-align: center">
            <span data-bind="template:{name:'site_link', data:owner}"></span>
            <br/>
            <img data-bind="attr: {src: owner.avatar.thumbSrc}"/>
        </div>

        <div class="span8">
            <div data-bind="html: html"></div>
            <span class="pull-right" data-bind="timestamp: dateCreated"></span>
        </div>
    </div>
</mk:tmpl>

<g:render template="/jquery-tmpl/site"/>
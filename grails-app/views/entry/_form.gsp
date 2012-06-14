<%@ page import="mirari.struct.Entry" %>



<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'owner', 'error')} ">
	<label for="owner">
		<g:message code="entry.owner.label" default="Owner" />
		
	</label>
	<g:select id="owner" name="owner.id" from="${mirari.Site.list()}" optionKey="id" required="" value="${entryInstance?.owner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: entryInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="entry.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${entryInstance?.title}" />
</div>


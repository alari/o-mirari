ko.bindingHandlers.sortableItem =
  init: (element, valueAccessor)->
    $(element).data "sortItem", valueAccessor()

ko.bindingHandlers.sortableInners =
  init: (element,valueAccessor,allBindingsAccessor,context)->
    $(element).data "sortUnit", valueAccessor()
    $(element).sortable
      update: (event, ui)->
        item = ui.item.data("sortItem");
        if (item)
          newParent = ui.item.parent().data("sortUnit")
          position = ko.utils.arrayIndexOf(ui.item.parent().children(), ui.item[0]);
          item.sortTo(newParent, position)
          ui.item.remove()
      handle: ".sort"
      connectWith: '.sortable'
      tolerance: "pointer"
      placeholder: "sortable-placeholder"
      opacity: 0.65
      start: (event, ui)->
        $(document.body).addClass "sortable-process"
      stop: (event, ui)->
        $(document.body).removeClass "sortable-process"



  ko.bindingHandlers.sortableItem = {
    init: function(element, valueAccessor) {
      return $(element).data("sortItem", valueAccessor());
    }
  };

  ko.bindingHandlers.sortableInners = {
    init: function(element, valueAccessor, allBindingsAccessor, context) {
      $(element).data("sortUnit", valueAccessor());
      return $(element).sortable({
        update: function(event, ui) {
          var item, newParent, position;
          item = ui.item.data("sortItem");
          if (item) {
            newParent = ui.item.parent().data("sortUnit");
            position = ko.utils.arrayIndexOf(ui.item.parent().children(), ui.item[0]);
            item.sortTo(newParent, position);
            return ui.item.remove();
          }
        },
        handle: ".sort",
        connectWith: '.sortable',
        tolerance: "pointer",
        placeholder: "sortable-placeholder",
        opacity: 0.65,
        start: function(event, ui) {
          return $(document.body).addClass("sortable-process");
        },
        stop: function(event, ui) {
          return $(document.body).removeClass("sortable-process");
        }
      });
    }
  };

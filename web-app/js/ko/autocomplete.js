
  ko.bindingHandlers.autocomplete = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      return $.ajax({
        url: valueAccessor(),
        dataType: "json",
        success: function(json) {
          return $(element).autocomplete({
            source: json
          });
        }
      });
    }
  };

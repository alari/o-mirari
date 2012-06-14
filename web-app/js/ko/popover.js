
  ko.bindingHandlers.popover = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var params;
      params = valueAccessor();
      return $(element).popover(params);
    }
  };

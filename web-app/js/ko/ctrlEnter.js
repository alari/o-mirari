
  ko.bindingHandlers.ctrlEnter = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var action;
      action = valueAccessor();
      return $(element).keydown(function(e) {
        if (e.ctrlKey && (e.keyCode === 13 || e.keyCode === 10)) {
          return action.call();
        }
      });
    }
  };

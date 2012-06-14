
  ko.bindingHandlers.timestamp = {
    init: function(element, valueAccessor) {
      var d;
      d = new Date(ko.utils.unwrapObservable(valueAccessor()));
      return $(element).text(d.toLocaleDateString());
    }
  };


  $(function() {
    return ko.bindingHandlers.autoResize = {
      init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
        var params, _ref, _ref2, _ref3;
        params = valueAccessor();
        if ((_ref = params.maxHeight) == null) params.maxHeight = 10000;
        if ((_ref2 = params.minHeight) == null) params.minHeight = 120;
        if ((_ref3 = params.extraSpace) == null) params.extraSpace = 20;
        return $(element).autoResize(params);
      }
    };
  });

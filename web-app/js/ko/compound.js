
  ko.bindingHandlers.compound = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var compoundVM, params;
      params = valueAccessor();
      compoundVM = new CompoundUnitVM(params);
      return allBindingsAccessor().template.data = compoundVM;
    }
  };

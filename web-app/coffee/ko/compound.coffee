ko.bindingHandlers.compound =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    params = valueAccessor()
    compoundVM = new CompoundUnitVM(params)
    allBindingsAccessor().template.data = compoundVM
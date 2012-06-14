ko.bindingHandlers.popover =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    params = valueAccessor()
    $(element).popover params
ko.bindingHandlers.ctrlEnter =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    action = valueAccessor()
    $(element).keydown (e)->
      if e.ctrlKey and (e.keyCode == 13 or e.keyCode == 10)
        action.call()
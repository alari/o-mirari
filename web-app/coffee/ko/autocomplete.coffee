ko.bindingHandlers.autocomplete =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    $.ajax
      url: valueAccessor()
      dataType:"json"
      success: (json)->
        $(element).autocomplete
          source: json
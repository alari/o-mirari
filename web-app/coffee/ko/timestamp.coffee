ko.bindingHandlers.timestamp =
  init: (element, valueAccessor) ->
    d = new Date(ko.utils.unwrapObservable(valueAccessor()))
    $(element).text d.toLocaleDateString()
ko.bindingHandlers.fadeOut =
  init: (element, valueAccessor) ->
    params = valueAccessor()

    fadeOutSpeed = if params.speed then params.speed else "slow"
    fadeAfterCall = if params.after then params.after else ->
    fadeDelay = if params.delay then params.delay else 5

    fadeFunc = ->
      $(element).fadeOut fadeOutSpeed, ->
        console.log fadeAfterCall
        fadeAfterCall.call()
    setTimeout fadeFunc, fadeDelay * 1000

  ko.bindingHandlers.fadeOut = {
    init: function(element, valueAccessor) {
      var fadeAfterCall, fadeDelay, fadeFunc, fadeOutSpeed, params;
      params = valueAccessor();
      fadeOutSpeed = params.speed ? params.speed : "slow";
      fadeAfterCall = params.after ? params.after : function() {};
      fadeDelay = params.delay ? params.delay : 5;
      fadeFunc = function() {
        return $(element).fadeOut(fadeOutSpeed, function() {
          console.log(fadeAfterCall);
          return fadeAfterCall.call();
        });
      };
      return setTimeout(fadeFunc, fadeDelay * 1000);
    }
  };

ko.bindingHandlers.carousel =
    init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      $(".item:first", element).addClass("active")

      maxH = 0

      $(".item", element).each (n, e)->
        h = $(e).height()
        if h > maxH
          maxH = h

      $(".item", element).each (n, e)->
        $(e).css("height", maxH)
      $(element).css("height", maxH)

      $(element).addClass "carousel"
      $(element).addClass "slide"
      $(element).carousel()
      $(element).carousel "cycle"

      $(element).find(".carousel-control.left").click ->
        $(element).carousel 'prev'

      $(element).find(".carousel-control.right").click ->
        $(element).carousel 'next'
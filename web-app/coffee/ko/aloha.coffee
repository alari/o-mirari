ko.bindingHandlers.aloha =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    $(element).attr "contenteditable", true
    #Aloha.ready -> Aloha.jQuery(element).aloha()

    $(element).focus ->
      $this = $(this)
      $this.data 'html-before', $this.html()
    $(element).bind 'change', ->
      $this = $(this)
      html = $this.html()
      if $this.data('html-before') isnt html
        $this.data 'html-before', html
      # TODO: get from value accessor
        viewModel.params.text = html

    $(element).bind 'blur keyup', ->
      $(this).trigger('change')

    $(element).bind 'paste', ->
      $this = $(this)
      setTimeout((->
        $this.trigger 'change'
      ), 100)
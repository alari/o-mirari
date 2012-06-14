
  ko.bindingHandlers.aloha = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      $(element).attr("contenteditable", true);
      $(element).focus(function() {
        var $this;
        $this = $(this);
        return $this.data('html-before', $this.html());
      });
      $(element).bind('change', function() {
        var $this, html;
        $this = $(this);
        html = $this.html();
        if ($this.data('html-before') !== html) {
          $this.data('html-before', html);
          return viewModel.params.text = html;
        }
      });
      $(element).bind('blur keyup', function() {
        return $(this).trigger('change');
      });
      return $(element).bind('paste', function() {
        var $this;
        $this = $(this);
        return setTimeout((function() {
          return $this.trigger('change');
        }), 100);
      });
    }
  };

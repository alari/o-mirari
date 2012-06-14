
  ko.bindingHandlers.carousel = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var maxH;
      $(".item:first", element).addClass("active");
      maxH = 0;
      $(".item", element).each(function(n, e) {
        var h;
        h = $(e).height();
        if (h > maxH) return maxH = h;
      });
      $(".item", element).each(function(n, e) {
        return $(e).css("height", maxH);
      });
      $(element).css("height", maxH);
      $(element).addClass("carousel");
      $(element).addClass("slide");
      $(element).carousel();
      $(element).carousel("cycle");
      $(element).find(".carousel-control.left").click(function() {
        return $(element).carousel('prev');
      });
      return $(element).find(".carousel-control.right").click(function() {
        return $(element).carousel('next');
      });
    }
  };

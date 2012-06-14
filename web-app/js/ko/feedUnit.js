
  ko.bindingHandlers.feedUnit = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var feedVM, params;
      params = valueAccessor();
      feedVM = new FeedUnitVM(params.url, params.drafts, params.style, params.last);
      feedVM.loadJson(0);
      return allBindingsAccessor().template.data = feedVM;
    }
  };

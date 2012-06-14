
  ko.bindingHandlers.audio = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var params;
      params = valueAccessor();
      return $(element).mediaelementplayer({
        pluginPath: params,
        audioWidth: 400,
        audioHeight: 30,
        startVolume: 0.8,
        loop: false,
        enableAutosize: true,
        features: ['playpause', 'progress', 'current', 'duration', 'tracks', 'volume', 'fullscreen'],
        startLanguage: '',
        translations: [],
        translationSelector: false,
        googleApiKey: ''
      });
    }
  };

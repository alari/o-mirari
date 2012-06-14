ko.bindingHandlers.audio =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    params = valueAccessor()
    $(element).mediaelementplayer(
      pluginPath: params
      #// width of audio player
      audioWidth: 400
      #// height of audio player
      audioHeight: 30
      #// initial volume when the player starts
      startVolume: 0.8
      #// useful for <audio> player loops
      loop: false
      #// enables Flash and Silverlight to resize to content size
      enableAutosize: true
      #// the order of controls you want on the control bar (and other plugins below)
      features: ['playpause','progress','current','duration','tracks','volume','fullscreen']

      #// automatically selects a <track> element
      startLanguage: ''
      #// a list of languages to auto-translate via Google
      translations: []
      #// a dropdownlist of automatic translations
      translationSelector: false
      #// key for tranlsations
      googleApiKey: ''
    )
exports = this
class exports.LinkVM
  constructor: ->
    @code = ko.observable ""
    @title = ko.observable ""

  fromJson: (json)->
    @code json.code
    @title json.title
    this

  go: =>
    alert "Going to: "+@code()
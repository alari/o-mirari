exports = this
class exports.PileCardVM extends CardVM
  tmpl: "stack"

  constructor: ->
    super()
    @entries = ko.observableArray []

  fromJson: (json)=>
    super json
    @entries.push new EntryCardVM().fromJson(e) for e in json.entries
    this
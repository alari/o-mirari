exports = this
class exports.EntryCardVM extends CardVM
  tmpl: "entry"

  constructor: ->
    super()
    @blocks = ko.observableArray []
    @stacks = ko.observableArray []

  fromJson: (json)=>
    super json
    @blocks.push new BlockVM().fromJson(b) for b in json.blocks
    @stacks.push new LinkVM().fromJson(b) for b in json.stacks
    this
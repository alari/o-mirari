exports = this
class exports.BlocksHolderVM
  constructor: ->
    @id = ko.observable null
    @blocks = ko.observableArray []
    @entry = ko.observable null
  fromJson: (json)=>
    mapping =
      blocks:
        create: (o)-> if o.data then new BlockVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      entry:
        create: (o)-> if o.data then new EntryVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
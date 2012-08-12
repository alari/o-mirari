exports = this
class exports.mirari.data.BlocksHolderVM
  constructor: ->
    @id = ko.observable null
    @blocks = ko.observableArray []
    @entry = ko.observable null
    @id = ko.observable null
    @version = ko.observable null
  fromJson: (json)=>
    mapping =
      blocks:
        create: (o)-> if o.data then new mirari.data.BlockVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      entry:
        create: (o)-> if o.data then new mirari.struct.EntryVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
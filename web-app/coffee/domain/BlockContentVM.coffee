exports = this
class exports.BlockContentVM
  constructor: ->
    @id = ko.observable null
    @block = ko.observable null
    @text = ko.observable null
  fromJson: (json)=>
    mapping =
      block:
        create: (o)-> if o.data then new BlockVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
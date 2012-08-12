exports = this
class exports.mirari.data.BlockContentVM
  constructor: ->
    @id = ko.observable null
    @block = ko.observable null
    @id = ko.observable null
    @text = ko.observable null
    @version = ko.observable null
  fromJson: (json)=>
    mapping =
      block:
        create: (o)-> if o.data then new mirari.data.BlockVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
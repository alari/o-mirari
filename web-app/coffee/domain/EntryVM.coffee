exports = this
class exports.mirari.struct.EntryVM
  constructor: ->
    @id = ko.observable null
    @blocksHolder = ko.observable null
    @dateCreated = ko.observable null
    @id = ko.observable null
    @lastUpdated = ko.observable null
    @owner = ko.observable null
    @stringId = ko.observable null
    @title = ko.observable null
    @version = ko.observable null
  fromJson: (json)=>
    mapping =
      owner:
        create: (o)-> if o.data then new mirari.SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
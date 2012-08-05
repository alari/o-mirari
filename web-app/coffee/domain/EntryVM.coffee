exports = this
class exports.EntryVM
  constructor: ->
    @id = ko.observable null
    @dateCreated = ko.observable null
    @lastUpdated = ko.observable null
    @owner = ko.observable null
    @title = ko.observable null
  fromJson: (json)=>
    mapping =
      owner:
        create: (o)-> if o.data then new SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
exports = this
class exports.BlockVM
  constructor: ->
    @id = ko.observable null
    @content = ko.observable null
    @dateCreated = ko.observable null
    @holder = ko.observable null
    @lastUpdated = ko.observable null
    @owner = ko.observable null
    @title = ko.observable null
    @type = ko.observable null
  fromJson: (json)=>
    mapping =
      content:
        create: (o)-> if o.data then new BlockContentVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      holder:
        create: (o)-> if o.data then new BlocksHolderVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      owner:
        create: (o)-> if o.data then new SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
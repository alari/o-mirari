exports = this
class exports.mirari.data.BlockVM
  constructor: ->
    @id = ko.observable null
    @content = ko.observable null
    @contentFile = ko.observable null
    @contentPolicy = ko.observable null
    @contentUrl = ko.observable null
    @dateCreated = ko.observable null
    @holder = ko.observable null
    @id = ko.observable null
    @lastUpdated = ko.observable null
    @owner = ko.observable null
    @title = ko.observable null
    @type = ko.observable null
    @version = ko.observable null
  fromJson: (json)=>
    mapping =
      content:
        create: (o)-> if o.data then new mirari.data.BlockContentVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      holder:
        create: (o)-> if o.data then new mirari.data.BlocksHolderVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      owner:
        create: (o)-> if o.data then new mirari.SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
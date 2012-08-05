exports = this
class exports.SiteVM
  constructor: ->
    @id = ko.observable null
    @account = ko.observable null
    @dateCreated = ko.observable null
    @displayName = ko.observable null
    @host = ko.observable null
    @kind = ko.observable null
    @lastUpdated = ko.observable null
    @name = ko.observable null
    @portal = ko.observable null
  fromJson: (json)=>
    mapping =
      account:
        create: (o)-> if o.data then new AccountVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      portal:
        create: (o)-> if o.data then new SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
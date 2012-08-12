exports = this
class exports.mirari.SiteVM
  constructor: ->
    @id = ko.observable null
    @account = ko.observable null
    @dateCreated = ko.observable null
    @displayName = ko.observable null
    @host = ko.observable null
    @id = ko.observable null
    @kind = ko.observable null
    @lastUpdated = ko.observable null
    @name = ko.observable null
    @portal = ko.observable null
    @portalSite = ko.observable null
    @profileSite = ko.observable null
    @subSite = ko.observable null
    @url = ko.observable null
    @version = ko.observable null
  fromJson: (json)=>
    mapping =
      account:
        create: (o)-> if o.data then new mirari.security.AccountVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
      portal:
        create: (o)-> if o.data then new mirari.SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
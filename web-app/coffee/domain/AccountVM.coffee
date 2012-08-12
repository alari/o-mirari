exports = this
class exports.mirari.security.AccountVM
  constructor: ->
    @id = ko.observable null
    @authorities = ko.observable null
    @authorityNames = ko.observable null
    @email = ko.observable null
    @id = ko.observable null
    @mainProfile = ko.observable null
    @version = ko.observable null
  fromJson: (json)=>
    mapping =
      mainProfile:
        create: (o)-> if o.data then new mirari.SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
exports = this
class exports.AccountVM
  constructor: ->
    @id = ko.observable null
    @email = ko.observable null
    @mainProfile = ko.observable null
  fromJson: (json)=>
    mapping =
      mainProfile:
        create: (o)-> if o.data then new SiteVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
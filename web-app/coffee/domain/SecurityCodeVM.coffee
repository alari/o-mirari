exports = this
class exports.SecurityCodeVM
  constructor: ->
    @id = ko.observable null
    @account = ko.observable null
    @dateCreated = ko.observable null
    @token = ko.observable null
  fromJson: (json)=>
    mapping =
      account:
        create: (o)-> if o.data then new AccountVM().fromJson(o.data) else null
        key: (o)-> ko.utils.unwrapObservable o.id
    ko.mapping.fromJS json, mapping, this
    this
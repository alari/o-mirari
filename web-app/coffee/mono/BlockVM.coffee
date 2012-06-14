exports = this
class exports.BlockVM
  constructor: ->
    @id = ko.observable ""

  fromJson: (json)=>
    @id = ko.observable json.id
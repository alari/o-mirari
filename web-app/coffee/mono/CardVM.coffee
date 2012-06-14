exports = this
class exports.CardVM
  constructor: ->
    @link = null
    @header = ko.observable ""

  fromJson: (json)=>
    @header json.header
    @link = new LinkVM().fromJson(json.link)
    this
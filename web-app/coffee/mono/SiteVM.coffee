exports = this

class exports.SiteVM
  constructor: ->
    @cards = ko.observableArray []
    @current = ko.observable null

    @menu = ko.observableArray []

  pushCard: (card)=>
    @cards.push card
    if not @current()
      @current card

  setCurrentN: (n)=>

  fromJson: (json)=>
    @pushCard new PileCardVM().fromJson(json.root)
    @menu.push new LinkVM().fromJson(m) for m in json.menu
    this

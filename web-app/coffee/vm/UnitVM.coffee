exports = this
$ = exports.jQuery
$ ->
  class exports.UnitVM
    constructor: (@_parent, json)->
      @title = ko.observable(json.title)
      @id = json.id
      @type = json.type
      @params = json.params || {}
      @image = if json.image then new ImageVM().fromJson(json.image) else null
      @inners = ko.observableArray([])
      @innersCount = ko.computed =>
        (u for u in @.inners() when not u._destroy).length

      @innersVisible = ko.observable(true)
      @contentVisible = ko.observable(true)

      @uniqueName = new Date().getMilliseconds().toString() + "unit" + new Date().getUTCSeconds().toString()

    remove: =>
      @_parent.inners.destroy this

    sortTo: (newParent, position)=>
      if (position >= 0)
        @_parent.inners.remove this
        @_parent = newParent
        @_parent.inners.splice position, 0, this

    toggleInnersVisibility: =>
      @innersVisible(not @innersVisible())

    toggleContentVisibility: =>
      @contentVisible(not @contentVisible())

    addTextUnit: =>
      UnitUtils.addTextUnit(this)

    canSort: =>
      UnitUtils.isSortable(this)

    canRemove: =>
      UnitUtils.isRemoveable(this)

    canHoldInners: =>
      UnitUtils.isContainer(this)
exports = this
class exports.PageVM
  constructor: ->
    @inners = ko.observableArray []

    @tags = ko.observableArray []

    @title = ko.observable ""

    @id = ko.observable ""

    @type = ko.observable "entry"

    @draft = ko.observable true

    @url = ko.observable "."

    @innersCount = ko.computed =>
      (u for u in @inners() when not u._destroy).length

    @avatar = new AvatarVM()
    @image = new ImageVM()

    @tagAct = new TagEditAct(this) if TagEditAct?
    @editAct = new PageEditAct(this)
    @innersAct = new PageInnersAct(this)
    @bottomMenuHelper = new BottomMenuHelper(this)

  unitTmpl: (unit)->
    "edit_"+unit.type

  toJson: ->
    ko.mapping.toJSON this,
      ignore: ["_parent", "toJson", "avatar", "image", "innersCount", "innersVisible", "contentVisible", "tagAct", "editAct", "innersAct", "uniqueName", "bottomMenuHelper"]

  fromJson: (json)->
    @inners.removeAll()
    @tags.removeAll()

    @title json.title
    @id json.id
    @type json.type
    @draft json.draft

    @url json.url

    @image.fromJson(json.image)
    @avatar.fromJson(json.avatar)

    @innersAct.addUnit(u) for u in json.inners
    @tagAct.pushJson(t) for t in json.tags

#
#       Helper VM for bottom menu
#
class BottomMenuHelper
  constructor: (@vm)->
    @tagsVisible = ko.observable false
    @moreVisible = ko.observable false

  updateHeight: =>
    $(".page-bottom-spacer").css "height", $('.page-bottom-edit-menu').height()

  hideTags: =>
    @tagsVisible false
    @updateHeight()

  hideMore: =>
    @moreVisible false
    @updateHeight()

  toggleMore: =>
    @moreVisible !@moreVisible()
    @updateHeight()

  toggleTags: =>
    @tagsVisible !@tagsVisible()
    @updateHeight()

#
#       Actions to do with Inner Units of the Page
#
class PageInnersAct
  constructor: (@vm)->

  addUnit: (unitJson)=>
    UnitUtils.addUnitJson @vm, unitJson

  addExternalUnit: =>
    UnitUtils.addExternalUnit @vm

  addTextUnit: =>
    UnitUtils.addTextUnit @vm

  addCompoundUnit: (compoundType)=>
    UnitUtils.addCompoundUnit(@vm, compoundType)

  addRenderInnersUnit: =>
    UnitUtils.addRenderInnersUnit @vm

  addFeedUnit: =>
    UnitUtils.addFeedUnit @vm

  hideAllInners: =>
    UnitUtils.walk(node, (n)-> n.innersVisible(false) if n.innersCount() > 0) for node in @vm.inners()

  showAllInners: =>
    UnitUtils.walk(node, (n)-> n.innersVisible(true) if n.innersCount() > 0) for node in @vm.inners()

  hideAllContent: =>
    UnitUtils.walk(node, (n)-> n.contentVisible(false)) for node in @vm.inners()

  showAllContent: =>
    UnitUtils.walk(node, (n)-> n.contentVisible(true)) for node in @vm.inners()

#
#       Actions to do with Page whilst edit
#
class PageEditAct
  constructor: (@vm)->

  url: (action)=>
    @vm.url()+"/"+action

  saveAndContinue: =>
      return false if UnitUtils.isEmpty @vm
      _t = @vm
      jsonPostReact @url("saveAndContinue"), {ko: @vm.toJson()}, (mdl) =>
        _t.fromJson(mdl.page)

  submitDraft: =>
      @vm.draft true
      @submit()

  submitPub: =>
      @vm.draft false
      @submit()

  submit: =>
      return false if UnitUtils.isEmpty @vm
      jsonPostReact @url("save"), {draft: @vm.draft(), ko: @vm.toJson()}, (mdl) ->
        console.log mdl

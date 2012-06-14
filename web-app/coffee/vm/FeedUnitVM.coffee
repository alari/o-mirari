exports = this
class exports.FeedUnitVM extends PageAnnounces
  constructor: (@feedUrl, @draftsUrl, @style, @lastStyle)->
    super()
    @last = ko.observable null
    @lastTemplate = "announce_"+@lastStyle

    @page = ko.observable 0
    @pagesTemplate = "announces_"+@style

    @hasMorePages = ko.observable true

    @draftsVisible = ko.observable false

    @drafts = ko.observableArray []
    @draftsCount = ko.computed => @drafts().length
    jsonGetReact @draftsUrl, (json)=>
      @types = json.types if json.types
      if json.pages?.length
        @drafts.push new PageAnnounceVM(this, p) for p in json.pages

  toggleDrafts: =>
    @draftsVisible !@draftsVisible()

  fromJson: (json)=>
    @types = json.types if json.types
    @pages.push new PageAnnounceVM(this, p) for p in json.pages
    @last new PageAnnounceVM(this, json.last) if json.last

  loadJson: (page)=>
    jsonGetReact @feedUrl + "?entry=" + page, (json)=>
      @hasMorePages json.pages?.length
      @fromJson(json)
      @page page

  loadPage: =>
    @loadJson @page()+1
exports = this
class exports.PageAnnounceVM
  constructor: (@_parent, json)->
    @id = json.id

    @image = new ImageVM().fromJson(json.image)
    @url = json.url
    @title = json.title

    @date = json.date

    @type = json.type
    @typeString = ko.observable @_parent.typeToString(@type)

    @owner = new OwnerVM().fromJson(json.owner)

    @html = ko.observable ""

    if @_parent.style is "blog"
      @html "loading..."
      jsonGetReact @url + "/firstUnit", (json)=>
        @html json.html
    if @_parent.style is "full"
      @html "loading full..."
      jsonGetReact @url + "/fullHtml", (json)=>
        @html json.html

class exports.PageAnnounces
  constructor: ->
    @pages = ko.observableArray []
    @types = {}

  typeToString: (type)->
    @types[type]

  loadJson: (url)=>
    jsonGetReact url, (json)=>
      @types = json.types
      for p in json.pages
        @pages.push new PageAnnounceVM(this, p)
exports = this

class exports.CommentVM
  constructor: (@_parent)->
    @id = null
    @text = ""
    @html = ""
    @owner = new OwnerVM()
    @title = ""
    @dateCreated = null
    @replies = ko.observableArray([])
    @canPostReply = @_parent.canPostComment

    @newReply = new NewReply(this)

  fromJson: (json) =>
    @id = json.id
    @text =json.text
    @html = json.html

    @owner.fromJson(json.owner)

    @title = json.title
    @dateCreated = json.dateCreated

    if json.replies?.length
      @pushReply(r) for r in json.replies
    this

  url: (action)=> @_parent.url(action)

  isCurrentProfileId: (id)->
    @_parent._profileId is id

  isCurrentPageOwner: ->
    @_parent.isPageOwner

  canRemove: ->
    @isCurrentProfileId(@owner.id) or @isCurrentPageOwner()

  remove: ->
    jsonPostReact @_parent.url("removeComment"), {commentId: @id}, (mdl)=>
      @_parent.removeComment this


  pushReply: (json)=>
    @replies.push new ReplyVM(this).fromJson(json)


class NewReply
  constructor: (@vm)->
    @text = ko.observable ""

  clear: =>
    @text ""

  post: =>
    return null if not @text()

    jsonPostReact @vm.url("postReply"), {commentId: @vm.id, text: @text()}, (mdl) =>
      @clear()
      @vm.pushReply mdl.reply
exports = this
class exports.PageCommentsVM extends CommentsHolderVM
  constructor: (@pageUrl, ownerId, @_profileId, @canPostComment)->
    @isPageOwner = @_profileId == ownerId
    jsonGetReact @url("commentsVM"), (mdl) =>
      @fromJson(mdl)
    @newComment = new NewComment(this)

  fromJson: (json)->
    @pushComment(c) for c in json.comments if json.comments?.length
    this

  removeComment: (comment)=>
    @comments.remove comment


class NewComment
  constructor: (@vm)->
    @title = ko.observable ""
    @text = ko.observable ""

  clear: =>
    @title ""
    @text ""

  post: =>
    return false if not @text().length

    jsonPostReact @vm.url("postComment"), {title: @title(), text: @text()}, (mdl) =>
      @clear()
      @vm.pushComment(mdl.comment)

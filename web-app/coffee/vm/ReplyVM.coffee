exports = this
class exports.ReplyVM
  constructor: (@_parent)->
    @id = ""
    @html = ""
    @owner = new OwnerVM()
    @dateCreated = ""

  fromJson: (json)->
    @id = json.id
    @html = json.html
    @owner.fromJson(json.owner)
    @dateCreated = json.dateCreated
    this

  canRemove: =>
    @_parent.isCurrentProfileId(@owner.id) or @_parent.isCurrentPageOwner()

  remove: ->
    jsonPostReact @_parent.url("removeReply"), {replyId: @id}, (mdl)=>
      @_parent.replies.remove this
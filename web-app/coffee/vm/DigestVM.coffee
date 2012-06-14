exports = this
class NoticeVM
  constructor: (@digest)->
    @id = ""
    @reason = new ReasonVM(this)
    @watched = ko.observable false
    @type = ""
    @date = ""
    @canReact = false
    @visible = ko.observable false

    @page = null

  fromJson: (json)->
    @id = json.id
    @date = json.date
    @type = json.type
    @canReact = json.canReact

    @page = new PageAnnounceVM({typeToString:(s)->s}, json.page) if json.page
    @reason.fromJson(json.reason)
    @watched json.watched

    this

  remove: =>
    @digest.notices.remove this

  toggle: =>
    @visible !@visible()

  watch: =>
    if not @watched()
      @watched true
      jsonGetReact "/i/watch/"+@id, (json)=>
        null

class ReasonVM
  constructor: (@notice)->
    @type = ""

    @comment = null
    @actor = null

  tmpl: =>
    "reason_"+@type

  fromJson: (json)=>
    @type = @notice.type

    if(json.comment)
      holder = new CommentsHolderVM(@notice.page.url, @notice.digest.profileId)

      holder.isPageOwner = holder._profileId is @notice.page.owner.id

      holder.canPostComment = @notice.canReact
      holder.removeComment = (comment)=> @notice.remove()

      @comment = new CommentVM(holder).fromJson(json.comment)
      if(json.reply)
        @comment.pushReply json.reply
        @reply = @comment.replies()[0]

    if(json.actor)
      @actor = new OwnerVM().fromJson(json.actor)

class exports.DigestVM
  constructor: ->
    @notices = ko.observableArray []
    @profileId = ""
    @page = 0
    @hasMorePages = ko.observable true

  load: =>
    return false if not @hasMorePages

    jsonGetReact "/i/viewModel?entry="+@page, (json)=>
      @profileId = json.profileId
      return @hasMorePages(false) if not json.notices?.length
      @notices.push new NoticeVM(this).fromJson(n) for n in json.notices
      @page++
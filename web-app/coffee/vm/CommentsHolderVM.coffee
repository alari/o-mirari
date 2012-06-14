exports = this
class exports.CommentsHolderVM
  constructor: (@pageUrl, @_profileId)->

  pushComment: (json)->
    @comments.push new CommentVM(this).fromJson(json)

  comments: ko.observableArray []
  isPageOwner: false
  _profileId: null
  canPostComment: null
  pageUrl: ""

  removeComment: (comment)->
    comments.remove comment

  url: (action)->
    @pageUrl + "/" + action
(function() {
  var exports;

  exports = this;

  exports.CommentsHolderVM = (function() {

    function CommentsHolderVM(pageUrl, _profileId) {
      this.pageUrl = pageUrl;
      this._profileId = _profileId;
    }

    CommentsHolderVM.prototype.pushComment = function(json) {
      return this.comments.push(new CommentVM(this).fromJson(json));
    };

    CommentsHolderVM.prototype.comments = ko.observableArray([]);

    CommentsHolderVM.prototype.isPageOwner = false;

    CommentsHolderVM.prototype._profileId = null;

    CommentsHolderVM.prototype.canPostComment = null;

    CommentsHolderVM.prototype.pageUrl = "";

    CommentsHolderVM.prototype.removeComment = function(comment) {
      return comments.remove(comment);
    };

    CommentsHolderVM.prototype.url = function(action) {
      return this.pageUrl + "/" + action;
    };

    return CommentsHolderVM;

  })();

}).call(this);

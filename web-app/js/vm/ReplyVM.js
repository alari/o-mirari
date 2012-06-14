(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.ReplyVM = (function() {

    function ReplyVM(_parent) {
      this._parent = _parent;
      this.canRemove = __bind(this.canRemove, this);
      this.id = "";
      this.html = "";
      this.owner = new OwnerVM();
      this.dateCreated = "";
    }

    ReplyVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.html = json.html;
      this.owner.fromJson(json.owner);
      this.dateCreated = json.dateCreated;
      return this;
    };

    ReplyVM.prototype.canRemove = function() {
      return this._parent.isCurrentProfileId(this.owner.id) || this._parent.isCurrentPageOwner();
    };

    ReplyVM.prototype.remove = function() {
      var _this = this;
      return jsonPostReact(this._parent.url("removeReply"), {
        replyId: this.id
      }, function(mdl) {
        return _this._parent.replies.remove(_this);
      });
    };

    return ReplyVM;

  })();

}).call(this);

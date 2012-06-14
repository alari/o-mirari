(function() {
  var NoticeVM, ReasonVM, exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  NoticeVM = (function() {

    function NoticeVM(digest) {
      this.digest = digest;
      this.watch = __bind(this.watch, this);
      this.toggle = __bind(this.toggle, this);
      this.remove = __bind(this.remove, this);
      this.id = "";
      this.reason = new ReasonVM(this);
      this.watched = ko.observable(false);
      this.type = "";
      this.date = "";
      this.canReact = false;
      this.visible = ko.observable(false);
      this.page = null;
    }

    NoticeVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.date = json.date;
      this.type = json.type;
      this.canReact = json.canReact;
      if (json.page) {
        this.page = new PageAnnounceVM({
          typeToString: function(s) {
            return s;
          }
        }, json.page);
      }
      this.reason.fromJson(json.reason);
      this.watched(json.watched);
      return this;
    };

    NoticeVM.prototype.remove = function() {
      return this.digest.notices.remove(this);
    };

    NoticeVM.prototype.toggle = function() {
      return this.visible(!this.visible());
    };

    NoticeVM.prototype.watch = function() {
      var _this = this;
      if (!this.watched()) {
        this.watched(true);
        return jsonGetReact("/i/watch/" + this.id, function(json) {
          return null;
        });
      }
    };

    return NoticeVM;

  })();

  ReasonVM = (function() {

    function ReasonVM(notice) {
      this.notice = notice;
      this.fromJson = __bind(this.fromJson, this);
      this.tmpl = __bind(this.tmpl, this);
      this.type = "";
      this.comment = null;
      this.actor = null;
    }

    ReasonVM.prototype.tmpl = function() {
      return "reason_" + this.type;
    };

    ReasonVM.prototype.fromJson = function(json) {
      var holder,
        _this = this;
      this.type = this.notice.type;
      if (json.comment) {
        holder = new CommentsHolderVM(this.notice.page.url, this.notice.digest.profileId);
        holder.isPageOwner = holder._profileId === this.notice.page.owner.id;
        holder.canPostComment = this.notice.canReact;
        holder.removeComment = function(comment) {
          return _this.notice.remove();
        };
        this.comment = new CommentVM(holder).fromJson(json.comment);
        if (json.reply) {
          this.comment.pushReply(json.reply);
          this.reply = this.comment.replies()[0];
        }
      }
      if (json.actor) return this.actor = new OwnerVM().fromJson(json.actor);
    };

    return ReasonVM;

  })();

  exports.DigestVM = (function() {

    function DigestVM() {
      this.load = __bind(this.load, this);      this.notices = ko.observableArray([]);
      this.profileId = "";
      this.page = 0;
      this.hasMorePages = ko.observable(true);
    }

    DigestVM.prototype.load = function() {
      var _this = this;
      if (!this.hasMorePages) return false;
      return jsonGetReact("/i/viewModel?entry=" + this.page, function(json) {
        var n, _i, _len, _ref, _ref2;
        _this.profileId = json.profileId;
        if (!((_ref = json.notices) != null ? _ref.length : void 0)) {
          return _this.hasMorePages(false);
        }
        _ref2 = json.notices;
        for (_i = 0, _len = _ref2.length; _i < _len; _i++) {
          n = _ref2[_i];
          _this.notices.push(new NoticeVM(_this).fromJson(n));
        }
        return _this.page++;
      });
    };

    return DigestVM;

  })();

}).call(this);

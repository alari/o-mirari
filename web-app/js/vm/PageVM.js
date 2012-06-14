(function() {
  var BottomMenuHelper, PageEditAct, PageInnersAct, exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PageVM = (function() {

    function PageVM() {
      var _this = this;
      this.inners = ko.observableArray([]);
      this.tags = ko.observableArray([]);
      this.title = ko.observable("");
      this.id = ko.observable("");
      this.type = ko.observable("entry");
      this.draft = ko.observable(true);
      this.url = ko.observable(".");
      this.innersCount = ko.computed(function() {
        var u;
        return ((function() {
          var _i, _len, _ref, _results;
          _ref = this.inners();
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            u = _ref[_i];
            if (!u._destroy) _results.push(u);
          }
          return _results;
        }).call(_this)).length;
      });
      this.avatar = new AvatarVM();
      this.image = new ImageVM();
      if (typeof TagEditAct !== "undefined" && TagEditAct !== null) {
        this.tagAct = new TagEditAct(this);
      }
      this.editAct = new PageEditAct(this);
      this.innersAct = new PageInnersAct(this);
      this.bottomMenuHelper = new BottomMenuHelper(this);
    }

    PageVM.prototype.unitTmpl = function(unit) {
      return "edit_" + unit.type;
    };

    PageVM.prototype.toJson = function() {
      return ko.mapping.toJSON(this, {
        ignore: ["_parent", "toJson", "avatar", "image", "innersCount", "innersVisible", "contentVisible", "tagAct", "editAct", "innersAct", "uniqueName", "bottomMenuHelper"]
      });
    };

    PageVM.prototype.fromJson = function(json) {
      var t, u, _i, _j, _len, _len2, _ref, _ref2, _results;
      this.inners.removeAll();
      this.tags.removeAll();
      this.title(json.title);
      this.id(json.id);
      this.type(json.type);
      this.draft(json.draft);
      this.url(json.url);
      this.image.fromJson(json.image);
      this.avatar.fromJson(json.avatar);
      _ref = json.inners;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        this.innersAct.addUnit(u);
      }
      _ref2 = json.tags;
      _results = [];
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        t = _ref2[_j];
        _results.push(this.tagAct.pushJson(t));
      }
      return _results;
    };

    return PageVM;

  })();

  BottomMenuHelper = (function() {

    function BottomMenuHelper(vm) {
      this.vm = vm;
      this.toggleTags = __bind(this.toggleTags, this);
      this.toggleMore = __bind(this.toggleMore, this);
      this.hideMore = __bind(this.hideMore, this);
      this.hideTags = __bind(this.hideTags, this);
      this.updateHeight = __bind(this.updateHeight, this);
      this.tagsVisible = ko.observable(false);
      this.moreVisible = ko.observable(false);
    }

    BottomMenuHelper.prototype.updateHeight = function() {
      return $(".page-bottom-spacer").css("height", $('.page-bottom-edit-menu').height());
    };

    BottomMenuHelper.prototype.hideTags = function() {
      this.tagsVisible(false);
      return this.updateHeight();
    };

    BottomMenuHelper.prototype.hideMore = function() {
      this.moreVisible(false);
      return this.updateHeight();
    };

    BottomMenuHelper.prototype.toggleMore = function() {
      this.moreVisible(!this.moreVisible());
      return this.updateHeight();
    };

    BottomMenuHelper.prototype.toggleTags = function() {
      this.tagsVisible(!this.tagsVisible());
      return this.updateHeight();
    };

    return BottomMenuHelper;

  })();

  PageInnersAct = (function() {

    function PageInnersAct(vm) {
      this.vm = vm;
      this.showAllContent = __bind(this.showAllContent, this);
      this.hideAllContent = __bind(this.hideAllContent, this);
      this.showAllInners = __bind(this.showAllInners, this);
      this.hideAllInners = __bind(this.hideAllInners, this);
      this.addFeedUnit = __bind(this.addFeedUnit, this);
      this.addRenderInnersUnit = __bind(this.addRenderInnersUnit, this);
      this.addCompoundUnit = __bind(this.addCompoundUnit, this);
      this.addTextUnit = __bind(this.addTextUnit, this);
      this.addExternalUnit = __bind(this.addExternalUnit, this);
      this.addUnit = __bind(this.addUnit, this);
    }

    PageInnersAct.prototype.addUnit = function(unitJson) {
      return UnitUtils.addUnitJson(this.vm, unitJson);
    };

    PageInnersAct.prototype.addExternalUnit = function() {
      return UnitUtils.addExternalUnit(this.vm);
    };

    PageInnersAct.prototype.addTextUnit = function() {
      return UnitUtils.addTextUnit(this.vm);
    };

    PageInnersAct.prototype.addCompoundUnit = function(compoundType) {
      return UnitUtils.addCompoundUnit(this.vm, compoundType);
    };

    PageInnersAct.prototype.addRenderInnersUnit = function() {
      return UnitUtils.addRenderInnersUnit(this.vm);
    };

    PageInnersAct.prototype.addFeedUnit = function() {
      return UnitUtils.addFeedUnit(this.vm);
    };

    PageInnersAct.prototype.hideAllInners = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          if (n.innersCount() > 0) return n.innersVisible(false);
        }));
      }
      return _results;
    };

    PageInnersAct.prototype.showAllInners = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          if (n.innersCount() > 0) return n.innersVisible(true);
        }));
      }
      return _results;
    };

    PageInnersAct.prototype.hideAllContent = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          return n.contentVisible(false);
        }));
      }
      return _results;
    };

    PageInnersAct.prototype.showAllContent = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          return n.contentVisible(true);
        }));
      }
      return _results;
    };

    return PageInnersAct;

  })();

  PageEditAct = (function() {

    function PageEditAct(vm) {
      this.vm = vm;
      this.submit = __bind(this.submit, this);
      this.submitPub = __bind(this.submitPub, this);
      this.submitDraft = __bind(this.submitDraft, this);
      this.saveAndContinue = __bind(this.saveAndContinue, this);
      this.url = __bind(this.url, this);
    }

    PageEditAct.prototype.url = function(action) {
      return this.vm.url() + "/" + action;
    };

    PageEditAct.prototype.saveAndContinue = function() {
      var _t,
        _this = this;
      if (UnitUtils.isEmpty(this.vm)) return false;
      _t = this.vm;
      return jsonPostReact(this.url("saveAndContinue"), {
        ko: this.vm.toJson()
      }, function(mdl) {
        return _t.fromJson(mdl.page);
      });
    };

    PageEditAct.prototype.submitDraft = function() {
      this.vm.draft(true);
      return this.submit();
    };

    PageEditAct.prototype.submitPub = function() {
      this.vm.draft(false);
      return this.submit();
    };

    PageEditAct.prototype.submit = function() {
      if (UnitUtils.isEmpty(this.vm)) return false;
      return jsonPostReact(this.url("save"), {
        draft: this.vm.draft(),
        ko: this.vm.toJson()
      }, function(mdl) {
        return console.log(mdl);
      });
    };

    return PageEditAct;

  })();

}).call(this);

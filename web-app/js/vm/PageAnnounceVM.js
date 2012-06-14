(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PageAnnounceVM = (function() {

    function PageAnnounceVM(_parent, json) {
      var _this = this;
      this._parent = _parent;
      this.id = json.id;
      this.image = new ImageVM().fromJson(json.image);
      this.url = json.url;
      this.title = json.title;
      this.date = json.date;
      this.type = json.type;
      this.typeString = ko.observable(this._parent.typeToString(this.type));
      this.owner = new OwnerVM().fromJson(json.owner);
      this.html = ko.observable("");
      if (this._parent.style === "blog") {
        this.html("loading...");
        jsonGetReact(this.url + "/firstUnit", function(json) {
          return _this.html(json.html);
        });
      }
      if (this._parent.style === "full") {
        this.html("loading full...");
        jsonGetReact(this.url + "/fullHtml", function(json) {
          return _this.html(json.html);
        });
      }
    }

    return PageAnnounceVM;

  })();

  exports.PageAnnounces = (function() {

    function PageAnnounces() {
      this.loadJson = __bind(this.loadJson, this);      this.pages = ko.observableArray([]);
      this.types = {};
    }

    PageAnnounces.prototype.typeToString = function(type) {
      return this.types[type];
    };

    PageAnnounces.prototype.loadJson = function(url) {
      var _this = this;
      return jsonGetReact(url, function(json) {
        var p, _i, _len, _ref, _results;
        _this.types = json.types;
        _ref = json.pages;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          p = _ref[_i];
          _results.push(_this.pages.push(new PageAnnounceVM(_this, p)));
        }
        return _results;
      });
    };

    return PageAnnounces;

  })();

}).call(this);

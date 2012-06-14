(function() {
  var exports;

  exports = this;

  exports.UnitUtils = (function() {

    function UnitUtils() {}

    UnitUtils.addUnitJson = function(container, unitJson) {
      var u, unit, _i, _len, _ref;
      unit = new UnitVM(container, unitJson);
      if (unitJson.inners && unitJson.inners.length) {
        _ref = unitJson.inners;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          u = _ref[_i];
          this.addUnitJson(unit, u);
        }
      }
      container.inners.push(unit);
      return unit;
    };

    UnitUtils.addTextUnit = function(container) {
      return this.addUnitJson(container, {
        type: "text",
        id: null,
        text: "",
        title: null
      });
    };

    UnitUtils.addRenderInnersUnit = function(container) {
      return this.addUnitJson(container, {
        type: "renderInners",
        id: null,
        title: null
      });
    };

    UnitUtils.addExternalUnit = function(container) {
      var url,
        _this = this;
      url = prompt("YouTube, Russia.Ru");
      if (!url) return null;
      return jsonPostReact("/p/addExternal", {
        url: url
      }, function(mdl) {
        return _this.addUnitJson(container, mdl.unit);
      });
    };

    UnitUtils.addCompoundUnit = function(container, compoundType) {
      if (CompoundType[compoundType]) {
        return this.addUnitJson(container, {
          type: "compound",
          params: {
            type: compoundType
          }
        });
      }
    };

    UnitUtils.addFeedUnit = function(container) {
      return this.addUnitJson(container, {
        type: "feed",
        params: {
          locked: "",
          num: 4,
          source: "all",
          style: "grid"
        }
      });
    };

    UnitUtils.walk = function(unit, fnc) {
      var u, _i, _len, _ref, _results;
      fnc(unit);
      _ref = unit.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        _results.push(this.walk(u, fnc));
      }
      return _results;
    };

    UnitUtils.isEmpty = function(container) {
      var u, _i, _len, _ref;
      _ref = container.inners();
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        if (!u._destroy) if (!this.isEmpty(u)) return false;
      }
      if (container instanceof UnitVM) {
        if (container.type !== "text") return false;
        return !container.params.text;
      }
      return true;
    };

    UnitUtils.isContainer = function(unit) {
      var _ref;
      return (_ref = unit.type) !== "feed" && _ref !== "compound";
    };

    UnitUtils.isSortable = function(unit) {
      return true;
    };

    UnitUtils.isRemoveable = function(unit) {
      var u, _i, _len, _ref;
      _ref = unit.inners();
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        if (!this.isRemoveable(u)) return false;
      }
      if (unit.type !== "feed") return true;
      return !unit.params.locked;
    };

    return UnitUtils;

  })();

}).call(this);

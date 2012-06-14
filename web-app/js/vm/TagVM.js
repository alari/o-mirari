(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __indexOf = Array.prototype.indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (__hasProp.call(this, i) && this[i] === item) return i; } return -1; };

  exports = this;

  exports.TagVM = (function() {

    function TagVM(_parent, displayName) {
      this._parent = _parent;
      this.displayName = displayName;
      this.remove = __bind(this.remove, this);
      this.fromJson = __bind(this.fromJson, this);
      this.id = null;
    }

    TagVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.displayName = json.displayName;
      return this;
    };

    TagVM.prototype.remove = function() {
      return this._parent.tags.remove(this);
    };

    return TagVM;

  })();

  exports.TagEditAct = (function() {

    function TagEditAct(vm) {
      this.vm = vm;
      this.tagInputKey = __bind(this.tagInputKey, this);
      this.addNewTag = __bind(this.addNewTag, this);
      this.pushJson = __bind(this.pushJson, this);
    }

    TagEditAct.prototype.pushJson = function(json) {
      return this.vm.tags.push(new TagVM(this.vm).fromJson(json));
    };

    TagEditAct.prototype.push = function(value) {
      return this.vm.tags.push(new TagVM(this.vm, value));
    };

    TagEditAct.prototype.addNewTag = function(data, event) {
      var value, _ref;
      value = (_ref = event.target) != null ? _ref.value : void 0;
      if (value) this.push(value);
      return event.target.value = "";
    };

    TagEditAct.prototype.tagInputKey = function(data, event) {
      var input, keys, stops, _ref;
      keys = {
        backspace: [8],
        enter: [13],
        space: [32],
        comma: [44, 188],
        tab: [9]
      };
      stops = [13, 9];
      input = event.target;
      if (!input.value && event.which === 8) {
        this.vm.tags.remove(this.vm.tags()[this.vm.tags().length - 1]);
      }
      if (input.value && (_ref = event.which, __indexOf.call(stops, _ref) >= 0)) {
        this.push(input.value);
        input.value = "";
      }
      return true;
    };

    return TagEditAct;

  })();

}).call(this);

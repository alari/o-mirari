(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.ImageVM = (function() {

    function ImageVM() {
      this.fromJson = __bind(this.fromJson, this);      this.thumbSrc = ko.observable("");
      this.smallSrc = ko.observable("");
      this.mediumSrc = ko.observable("");
      this.standardSrc = ko.observable("");
      this.maxSrc = ko.observable("");
    }

    ImageVM.prototype.fromJson = function(json) {
      this.thumbSrc(json.thumbSrc);
      this.smallSrc(json.smallSrc);
      this.mediumSrc(json.mediumSrc);
      this.standardSrc(json.standardSrc);
      this.maxSrc(json.maxSrc);
      return this;
    };

    return ImageVM;

  })();

}).call(this);

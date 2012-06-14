exports = this
class exports.ImageVM
  constructor: ->
    @thumbSrc = ko.observable ""
    @smallSrc = ko.observable ""
    @mediumSrc = ko.observable ""
    @standardSrc = ko.observable ""
    @maxSrc = ko.observable ""


  fromJson: (json)=>
    @thumbSrc json.thumbSrc
    @smallSrc json.smallSrc
    @mediumSrc json.mediumSrc
    @standardSrc json.standardSrc
    @maxSrc json.maxSrc

    this
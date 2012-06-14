exports = this
class exports.AvatarVM extends ImageVM
  constructor: ->
    super()
    @id = ""
    @name = ""
    @basic = true

  fromJson: (json)=>
    super json
    @id = json.id
    @name = json.name
    @basic = json.basic
    this
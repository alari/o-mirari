exports = this
class exports.OwnerVM
  constructor: ->
    @id = ""
    @displayName = ""
    @avatar = new AvatarVM()
    @url = ""

  fromJson: (json)=>
    @id = json.id
    @displayName = json.displayName
    @avatar.fromJson(json.avatar)
    @url = json.url
    this
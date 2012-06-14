exports = this
class exports.TagVM
  constructor: (@_parent, @displayName) ->
    @id = null

  fromJson: (json) =>
    @id = json.id
    @displayName = json.displayName
    this

  remove: =>
    @_parent.tags.remove this

class exports.TagEditAct
  constructor: (@vm)->

  pushJson: (json)=>
    @vm.tags.push new TagVM(@vm).fromJson(json)

  push: (value)->
    @vm.tags.push new TagVM(@vm, value)

  addNewTag: (data, event)=>
    value = event.target?.value
    if(value)
      @push value
    event.target.value = ""

  tagInputKey: (data, event)=>
    keys =
      backspace: [8]
      enter:     [13]
      space:     [32]
      comma:     [44,188]
      tab:       [9]
    stops = [13, 9]
    input = event.target

    if(not input.value and event.which is 8)
      @vm.tags.remove @vm.tags()[@vm.tags().length-1]

    if input.value and  event.which in stops
      @push input.value
      input.value = ""

    true
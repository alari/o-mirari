exports = this

class exports.CompoundUnitVM

  inner: {}

  constructor: (@unit)->
    @type = @unit.params.type
    if not @unit.innersCount()
      CompoundType[@type].init this
    else
      CompoundType[@type].restore this

  getInner: (type)=>
    for u in @unit.inners()
      if u.type is type
        return u

class exports.CompoundType
  @poetry:
    init: (compound)->
      UnitUtils.addTextUnit(compound.unit)
    restore: (compound)->

package com.diceforge.app.temple.vo

import com.diceforge.app.shared.SnowflakeId

class TempleId(id: Long) : SnowflakeId(id) {
  constructor() : this(0)
}

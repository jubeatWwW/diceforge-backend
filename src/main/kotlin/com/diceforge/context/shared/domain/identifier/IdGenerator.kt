package com.diceforge.context.shared.domain.identifier

import java.io.Serializable

interface IdGenerator<Id : Serializable> {
  fun generate(): Id
}

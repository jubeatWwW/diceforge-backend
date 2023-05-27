package com.diceforge.context.shared.domain.identifier

import java.util.*

class UUIDIdGenerator : IdGenerator<String> {
  override fun generate() = UUID.randomUUID().toString()
}
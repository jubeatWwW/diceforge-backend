package com.diceforge.context.shared.domain

import com.diceforge.context.shared.domain.time.Timestamp

interface DomainEvent : DomainObject {
  @Suppress("unused")
  val occurredAt: Timestamp
}

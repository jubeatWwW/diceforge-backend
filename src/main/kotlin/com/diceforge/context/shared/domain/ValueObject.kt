package com.diceforge.context.shared.domain

import java.io.Serializable

@Suppress("unused")
interface ValueObject : DomainObject, Serializable {
  override fun equals(other: Any?): Boolean
}

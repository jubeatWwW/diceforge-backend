package com.diceforge.context.shared.domain

import jakarta.persistence.Version

@Suppress("unused")
interface OptimisticLockEntity : DomainObject {
  @get:Version
  var version: Long
}
package com.diceforge.app.shared

import org.jetbrains.annotations.NotNull

abstract class SnowflakeId(@NotNull val id: Long) : ValueObject<SnowflakeId> {
  fun loaded(): Boolean {
    return this.id > 0
  }

  override fun sameValueAs(other: SnowflakeId): Boolean {
    return this.id == other.id
  }
}

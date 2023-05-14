package com.diceforge.app.shared

import java.io.Serializable

interface Entity<E, ID : Serializable> {
  /**
   * Entities compare by identity, not by attributes.
   *
   * @param other The other entity.
   * @return true if the identities are the same, regardless of other attributes.
   */
  fun sameIdentityAs(other: E): Boolean
  fun id(): ID
}

package com.diceforge.app.shared

import java.io.Serializable

interface ValueObject<T> : Serializable {
  /**
   * Value objects compare by the values of their attributes, they don't have an identity.
   *
   * @param other The other value object.
   * @return <code>true</code> if the given value object's and this value object's attributes are the same.
   */
  fun sameValueAs(other: T): Boolean
}

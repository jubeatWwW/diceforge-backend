package com.diceforge.context.shared.domain

abstract class DomainException(
  val code: String,
  val args: List<Any>,
) : RuntimeException(code) {
}
package com.diceforge.common.cqrs

interface CommandHandler<C : Command<R>, R> {
  fun handle(command: C): R
}

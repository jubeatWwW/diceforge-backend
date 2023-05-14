package com.diceforge.app.shared

interface CommandHandler<C: Command<R>, R, ID> {
  fun handle(command: C, entityId: ID): R
}

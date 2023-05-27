package com.diceforge.common.cqrs

interface CommandHandlerProvider {
  fun getHandler(command: Command<*>): CommandHandler<*, *>
}

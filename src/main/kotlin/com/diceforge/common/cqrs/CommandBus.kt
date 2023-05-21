package com.diceforge.common.cqrs

import org.springframework.stereotype.Component

@Component
class CommandBus(private val commandHandlerProvider: CommandHandlerProvider) {
  @Suppress("unused", "unchecked_cast")
  fun <Result> execute(command: Command<Result>): Result {
    val handler = commandHandlerProvider.getHandler(command) as CommandHandler<Command<Result>, Result>
    return handler.handle(command)
  }
}

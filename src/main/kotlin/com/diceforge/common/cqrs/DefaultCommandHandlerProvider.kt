package com.diceforge.common.cqrs

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class DefaultCommandHandlerProvider(handlerList: List<CommandHandler<*, *>>) : AbstractCommandHandlerProvider() {
  private val handlers: ConcurrentHashMap<Class<Command<*>>, CommandHandler<*, *>>

  init {
    val handlers = ConcurrentHashMap<Class<Command<*>>, CommandHandler<*, *>>()

    for (handler in handlerList) {
      val commandClass = getCommandClassFromHandler(handler)

      if (commandClass != null) {
        handlers[commandClass] = handler
      }
    }

    this.handlers = handlers
  }

  override fun getHandlerOrNull(command: Command<*>): CommandHandler<*, *>? {
    return handlers[command.javaClass]
  }
}

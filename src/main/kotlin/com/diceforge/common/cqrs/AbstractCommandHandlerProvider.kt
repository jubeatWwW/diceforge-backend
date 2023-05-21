package com.diceforge.common.cqrs

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class AbstractCommandHandlerProvider : CommandHandlerProvider {
  final override fun getHandler(command: Command<*>): CommandHandler<*, *> {
    return getHandlerOrNull(command)
      ?: throw Exception("command handler not found. Command class is ${command.javaClass}")
  }

  protected abstract fun getHandlerOrNull(command: Command<*>): CommandHandler<*, *>?

  @Suppress("unchecked_cast")
  protected fun getCommandClassFromHandler(handler: CommandHandler<*, *>): Class<Command<*>>? {
    val type = getParameterizedType(handler.javaClass.genericInterfaces, CommandHandler::class.java)
      ?: return null

    return type.actualTypeArguments[0] as Class<Command<*>>
  }

  private fun getParameterizedType(genericInterfaces: Array<Type>, expectedRawType: Class<*>): ParameterizedType? {
    for (type in genericInterfaces) {
      if (type is ParameterizedType) {
        if (expectedRawType == type.rawType) {
          return type
        }
      }
    }

    return null
  }
}

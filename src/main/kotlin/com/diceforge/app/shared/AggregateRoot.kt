package com.diceforge.app.shared

import io.vavr.control.Either
import org.springframework.context.ApplicationContext
import java.lang.reflect.ParameterizedType
import java.io.Serializable
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.CompletionStage


abstract class AggregateRoot<E, ID : Serializable>(
  private val applicationContext: ApplicationContext,
  val entityId: ID,
) : Entity<E, ID> {
  protected abstract fun initialBehavior(): AggregateRootBehavior<ID>
  private val behavior: AggregateRootBehavior<ID> = initialBehavior()

  @Suppress("unchecked_cast")
  fun <R> handle(command: Command<R>): R {
    val commandHandler = behavior.handlers[command.javaClass] as CommandHandler<Command<R>, R, ID>
    return commandHandler.handle(command, entityId)
  }

  protected open fun getHandler(commandHandlerClass: Class<out CommandHandler<*, *, ID>>): CommandHandler<*, *, ID> {
    return applicationContext.getBean(commandHandlerClass)
  }

  class AggregateRootBehavior<ID>(handlers: Map<Class<Command<*>>, CommandHandler<*, *, ID>>) {
    val handlers: Map<Class<Command<*>>, CommandHandler<*, *, ID>>

    init {
      this.handlers = Collections.unmodifiableMap(handlers)
    }
  }


  class AggregateRootBehaviorBuilder<ID> {
    private val handlers: MutableMap<Class<Command<*>>, CommandHandler<*, *, ID>> = HashMap()

    fun setCommandHandler(handler: CommandHandler<*, *, ID>): AggregateRootBehaviorBuilder<ID> {
      val commandClass = getCommandClassFromHandler(handler)
        ?: throw IllegalArgumentException("CommandHandler must implement CommandHandler<Command>")
      handlers[commandClass] = handler
      return this
    }

    @Suppress("unchecked_cast")
    private fun getCommandClassFromHandler(handler: CommandHandler<*, *, ID>): Class<Command<*>>? {
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

    fun build(): AggregateRootBehavior<ID> {
      return AggregateRootBehavior(handlers)
    }
  }
}
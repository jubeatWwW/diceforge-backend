package com.diceforge.context.temple.application

import com.diceforge.common.cqrs.CommandHandler
import com.diceforge.context.temple.domain.DiceFaceTaken
import org.springframework.stereotype.Component
import java.time.Clock

@Component
class TakeDiceFaceCommandHandler(
  private val clock: Clock,
) : CommandHandler<TakeDiceFaceCommand, DiceFaceTaken> {
  override fun handle(command: TakeDiceFaceCommand): DiceFaceTaken {
    return DiceFaceTaken(command.templeId, clock.millis())
  }
}
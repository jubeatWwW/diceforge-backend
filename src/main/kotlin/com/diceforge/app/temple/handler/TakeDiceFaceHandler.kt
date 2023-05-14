package com.diceforge.app.temple.handler

import com.diceforge.app.shared.CommandFailure
import com.diceforge.app.shared.CommandHandler
import com.diceforge.app.temple.command.TakeDiceFace
import com.diceforge.app.temple.event.DiceFaceTaken
import com.diceforge.app.temple.vo.TempleId
import io.vavr.control.Either
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

typealias HandlerReturnType = CompletionStage<Either<CommandFailure, DiceFaceTaken>>

@Component
class TakeDiceFaceHandler : CommandHandler<TakeDiceFace, HandlerReturnType, TempleId> {
  override fun handle(
    command: TakeDiceFace,
    entityId: TempleId
  ): HandlerReturnType {
    val event = DiceFaceTaken(entityId)
    println("command handler")
    return CompletableFuture.completedStage(Either.right(event))
  }
}

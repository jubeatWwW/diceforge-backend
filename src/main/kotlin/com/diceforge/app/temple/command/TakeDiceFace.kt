package com.diceforge.app.temple.command

import com.diceforge.app.shared.Command
import com.diceforge.app.shared.CommandFailure
import com.diceforge.app.temple.event.DiceFaceTaken
import com.diceforge.app.temple.vo.TempleId
import io.vavr.control.Either
import java.util.concurrent.CompletionStage

data class TakeDiceFace(
  val templeId: TempleId,
  val playerId: Long,
  val diceFaceId: Long
) : Command<CompletionStage<Either<CommandFailure, DiceFaceTaken>>>

package com.diceforge.context.temple.application

import com.diceforge.common.cqrs.Command
import com.diceforge.context.player.domain.PlayerId
import com.diceforge.context.temple.domain.DiceFaceId
import com.diceforge.context.temple.domain.DiceFaceTaken
import com.diceforge.context.temple.domain.TempleId

data class TakeDiceFaceCommand(
  val playerId: PlayerId,
  val templeId: TempleId,
  val diceFaceId: DiceFaceId,
) : Command<DiceFaceTaken>

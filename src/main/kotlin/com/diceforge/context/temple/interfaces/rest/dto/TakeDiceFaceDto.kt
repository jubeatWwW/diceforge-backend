package com.diceforge.context.temple.interfaces.rest.dto

import com.diceforge.context.player.domain.PlayerId
import com.diceforge.context.temple.application.TakeDiceFaceCommand
import com.diceforge.context.temple.domain.DiceFaceId
import com.diceforge.context.temple.domain.TempleId

data class TakeDiceFaceDto(
  val templeId: TempleId,
  val diceFaceId: DiceFaceId,
) {
  fun toCommand(playerId: PlayerId) = TakeDiceFaceCommand(playerId, templeId, diceFaceId)
}

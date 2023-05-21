package com.diceforge.context.temple.interfaces.rest

import com.diceforge.common.cqrs.CommandBus
import com.diceforge.context.player.domain.PlayerId
import com.diceforge.context.temple.domain.DiceFaceTaken
import com.diceforge.context.temple.interfaces.rest.dto.TakeDiceFaceDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController()
@RequestMapping("temple")
class TempleController(private val commandBus: CommandBus) {
  @PostMapping("takeDiceFace")
  fun takeDiceFace(@RequestBody @Valid dto: TakeDiceFaceDto): DiceFaceTaken {
    val playerId: PlayerId = Random().nextLong()
    val command = dto.toCommand(playerId)

    return commandBus.execute(command)
  }
}
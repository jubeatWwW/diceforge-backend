package com.diceforge.app.interfaces.rest

import com.diceforge.app.shared.SnowflakeIdGenerator
import com.diceforge.app.temple.command.TakeDiceFace
import com.diceforge.app.temple.entity.Temple
import com.diceforge.app.temple.vo.TempleId
import org.springframework.context.ApplicationContext
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable
import java.util.concurrent.CompletionStage

@RestController("/temple")
class TempleController(val applicationContext: ApplicationContext, val snowflakeIdGenerator: SnowflakeIdGenerator) {
  @PostMapping("/takeDiceFace")
  fun takeDiceFace(): Callable<CompletionStage<ResponseEntity<*>>> {
    return Callable {
      val temple = Temple(applicationContext, TempleId(snowflakeIdGenerator.nextId()))
      val cmd = TakeDiceFace(temple.id(), 1, 1)
      val promise = temple.handle(cmd)
      promise.thenApply { either -> either.fold(
        { failure -> ResponseEntity.badRequest().body(failure) },
        { event -> ResponseEntity.ok(event) }
      ) }
    }
  }
}
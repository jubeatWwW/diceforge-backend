package com.diceforge.app.temple.entity

import com.diceforge.app.shared.AggregateRoot
import com.diceforge.app.temple.handler.TakeDiceFaceHandler
import com.diceforge.app.temple.vo.TempleId
import org.springframework.context.ApplicationContext

class Temple(applicationContext: ApplicationContext, id: TempleId) :
  AggregateRoot<Temple, TempleId>(applicationContext, id) {

  constructor(applicationContext: ApplicationContext) : this(applicationContext, TempleId())

  override fun sameIdentityAs(other: Temple): Boolean {
    return this.id() == other.id()
  }

  override fun id(): TempleId {
    return this.entityId
  }

  override fun initialBehavior(): AggregateRootBehavior<TempleId> {
    return AggregateRootBehaviorBuilder<TempleId>()
      .setCommandHandler(getHandler(TakeDiceFaceHandler::class.java))
      .build()
  }
}

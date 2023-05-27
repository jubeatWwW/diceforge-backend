package com.diceforge.context.shared.domain

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Transient
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import java.io.Serializable
import java.util.*


@MappedSuperclass
abstract class BaseAggregateRoot<AggregateRoot : BaseAggregateRoot<AggregateRoot, Id>, Id : Serializable> :
  BaseEntity<AggregateRoot, Id>() {
  @Transient
  private var domainEvents: MutableList<DomainEvent> = ArrayList()

  @DomainEvents
  protected fun domainEvents(): Collection<DomainEvent> {
    return Collections.unmodifiableList(domainEvents)
  }

  @Suppress("unused")
  protected fun registerDomainEvent(event: DomainEvent) {
    domainEvents.add(event)
  }

  @AfterDomainEventPublication
  protected fun clearDomainEvents() {
    domainEvents.clear()
  }
}
package com.diceforge.context.shared.domain

import jakarta.persistence.MappedSuperclass
import java.io.Serializable

@MappedSuperclass
abstract class BaseEntity<Entity : BaseEntity<Entity, Id>, Id : Serializable> : IdentifiedDomainObject<Id> {
}
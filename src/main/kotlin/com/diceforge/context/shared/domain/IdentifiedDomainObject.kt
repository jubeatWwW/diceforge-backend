package com.diceforge.context.shared.domain

import java.io.Serializable

@Suppress("unused")
interface IdentifiedDomainObject<Id : Serializable> : DomainObject {
  var id: Id?

  fun sameIdAs(domainObject: IdentifiedDomainObject<Id>): Boolean {
    return id == domainObject.id
  }
}
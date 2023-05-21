package com.diceforge.context.temple.domain

import com.diceforge.context.shared.domain.DomainEvent
import com.diceforge.context.shared.domain.time.Timestamp

data class DiceFaceTaken(val templeId: TempleId, override val occurredAt: Timestamp) : DomainEvent

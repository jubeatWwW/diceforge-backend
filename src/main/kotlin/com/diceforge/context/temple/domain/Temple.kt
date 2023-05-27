package com.diceforge.context.temple.domain

import com.diceforge.context.shared.domain.BaseAggregateRoot

class Temple(override var id: TempleId?) : BaseAggregateRoot<Temple, TempleId>()

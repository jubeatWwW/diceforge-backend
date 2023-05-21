package com.diceforge.context.shared.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@Suppress("unused")
@NoRepositoryBean
interface BaseRepository<AggregateRoot : BaseAggregateRoot<AggregateRoot, Id>, Id : Serializable> :
  JpaRepository<AggregateRoot, Id>

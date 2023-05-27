package com.diceforge.context.shared.infrastructure.time

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class ClockConfig {
  @Bean
  fun clock(): Clock = Clock.systemUTC()
}
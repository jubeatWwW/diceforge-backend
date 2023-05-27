package com.diceforge.context.shared.domain.identifier

import java.net.NetworkInterface
import java.security.SecureRandom
import java.time.Instant

class SnowflakeIdGenerator : IdGenerator<Long> {
  private val nodeId: Long
  private val customEpoch: Long

  @Volatile
  private var lastTimestamp = -1L

  @Volatile
  private var sequence = 0L

  companion object {
    private const val UNUSED_BITS = 1 // Sign bit, Unused (always set to 0)
    private const val EPOCH_BITS = 41
    private const val NODE_ID_BITS = 10
    private const val SEQUENCE_BITS = 12

    private const val maxNodeId = (1L shl NODE_ID_BITS) - 1
    private const val maxSequence = (1L shl SEQUENCE_BITS) - 1

    private const val DEFAULT_CUSTOM_EPOCH = 1622505600000L // 2021-06-01

    private fun createNodeId(): Long {
      var nodeId: Long
      try {
        val sb = StringBuilder()
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
          val networkInterface = networkInterfaces.nextElement()
          val mac = networkInterface.hardwareAddress
          if (mac != null) {
            for (macPort in mac) {
              sb.append(String.format("%02X", macPort))
            }
          }
        }
        nodeId = sb.toString().hashCode().toLong()
      } catch (ex: Exception) {
        nodeId = (SecureRandom().nextInt()).toLong()
      }
      return nodeId and maxNodeId
    }
  }

  constructor(nodeId: Long, customEpoch: Long) {
    require(!(nodeId < 0 || nodeId > maxNodeId)) { "NodeId must be between 0 and $maxNodeId" }
    this.nodeId = nodeId
    this.customEpoch = customEpoch
  }

  @Suppress("unused")
  constructor(nodeId: Long) : this(nodeId, DEFAULT_CUSTOM_EPOCH)

  @Suppress("unused")
  constructor() : this(createNodeId(), DEFAULT_CUSTOM_EPOCH)

  private fun timestamp(): Long {
    return Instant.now().toEpochMilli() - customEpoch
  }

  private fun waitNextMillis(currentTimestamp: Long): Long {
    var localCurrentTimestamp = currentTimestamp
    while (localCurrentTimestamp == lastTimestamp) {
      localCurrentTimestamp = timestamp()
    }
    return localCurrentTimestamp
  }

  @Synchronized
  override fun generate(): Long {
    var currentTimestamp = timestamp()
    require(currentTimestamp >= lastTimestamp) { "Invalid System Clock!" }

    if (currentTimestamp == lastTimestamp) {
      sequence = (sequence + 1) and maxSequence
      if (sequence == 0L) {
        // Sequence Exhausted, wait till next millisecond.
        currentTimestamp = waitNextMillis(currentTimestamp)
      }
    } else {
      // reset sequence to start with zero for the next millisecond
      sequence = 0
    }

    lastTimestamp = currentTimestamp

    return currentTimestamp shl (NODE_ID_BITS + SEQUENCE_BITS) or (nodeId shl SEQUENCE_BITS) or sequence
  }

  fun parse(id: Long): List<Long> {
    val maskNodeId = ((1L shl NODE_ID_BITS) - 1) shl SEQUENCE_BITS
    val maskSequence = (1L shl SEQUENCE_BITS) - 1

    val timestamp = (id shr (NODE_ID_BITS + SEQUENCE_BITS)) + customEpoch
    val nodeId = (id and maskNodeId) shr SEQUENCE_BITS
    val sequence = id and maskSequence
    return listOf(timestamp, nodeId, sequence)
  }

  override fun toString(): String {
    return """
      |Snowflake Settings [EPOCH_BITS=$EPOCH_BITS, NODE_ID_BITS=$NODE_ID_BITS",
      | SEQUENCE_BITS=$SEQUENCE_BITS , CUSTOM_EPOCH=$customEpoch,
      | NodeId=$nodeId]
      """.trimMargin()
  }
}

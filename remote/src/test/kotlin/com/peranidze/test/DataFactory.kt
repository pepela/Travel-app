package com.peranidze.test

import java.util.*
import java.util.concurrent.ThreadLocalRandom


class DataFactory {

    companion object Factory {

        fun randomUuid(): String = java.util.UUID.randomUUID().toString()

        fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

        fun randomLong(): Long = randomInt().toLong()

        fun randomBoolean(): Boolean = Math.random() < 0.5

        fun randomDate(): Date = Date(randomLong())

        fun makeStringList(count: Int): List<String> = List(count) { randomUuid() }

    }

}
package com.example.knight.utils

import java.util.concurrent.atomic.AtomicInteger

/**
 * Generator for unique Ids
 */
object UniqueIdGenerator {
    private val mAtomicInteger = AtomicInteger(0)

    fun retrieveUniqueId(): Int {
        return mAtomicInteger.incrementAndGet()
    }
}
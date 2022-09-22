package com.example

import org.junit.Test
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.lang.Thread.sleep
import java.time.Duration

class CacheTest {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun cache() {
        logger.info("===== call slowFunc directly =====")
        slowFunc().block() // execute slowFunc!
        slowFunc().block() // execute slowFunc!

        logger.info("===== call cached =====")
        val cached = slowFunc()
        cached.block() // execute slowFunc!
        cached.block() // cached
    }

    @Test
    fun cacheMono() {

    }

    private fun slowFunc(): Mono<String> {
        return Mono.fromCallable {
            logger.info("execute slowFunc!")
            sleep(3000)
            "Result"
        }.cache(Duration.ofSeconds(10))
    }
}
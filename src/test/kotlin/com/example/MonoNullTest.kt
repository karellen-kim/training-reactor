package com.example

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.lang.Exception

class MonoNullTest {
    private val logger = LoggerFactory.getLogger(javaClass)

    data class Data(val value : Int)
    data class Response(val data: Data?)

    fun getData(): Response {
        return Response(Data(1))
    }

    fun getEmptyData(): Response {
        return Response(null)
    }

    @Test
    fun `mono에서 null이 반환되는 경우`() {
        runBlocking {
            val value = Mono.fromCallable {
                getData()
            }.map { it.data?.value }.awaitSingle()
            logger.info("value = ${value}")

            val emptyValue = Mono.fromCallable {
                getEmptyData() }
                .mapNotNull { it.data?.value }
                .awaitSingleOrNull()

            logger.info("emptyValue = ${emptyValue}")

        }
    }
}
package com.betclic

import com.betclic.controller.playerController
import com.betclic.repository.PlayerRepository
import com.betclic.service.PlayerService
import com.mongodb.client.MongoDatabase
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.litote.kmongo.KMongo

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    val playerModule = module {
        singleOf(::PlayerService)
        singleOf(::PlayerRepository)

        val mongoUri = environment.config.property("mongo.uri").getString()
        val mongoDatabase = environment.config.property("mongo.database").getString()
        val client = KMongo.createClient(mongoUri)
        single<MongoDatabase> { client.getDatabase(mongoDatabase) }
    }

    install(Koin) {
        modules(playerModule)
    }

    install(ContentNegotiation) {
        json()
    }

    install(Routing) {
        swaggerUI(path = "openapi")

        val playerService by inject<PlayerService>()
        playerController(playerService)
    }

    install(CORS) {
        anyHost()
        allowHeader(ContentType)
    }

}

package com.betclic.controller

import com.betclic.domain.Player
import com.betclic.service.PlayerService
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playerController(playerService: PlayerService) {

    route("/players") {
        
        post {
            val player = call.receive<Player>()
            call.respond(status = Created, message = playerService.addPlayer(player))
        }

        put {
            val player = call.receive<Player>()
            call.respond(playerService.updatePlayerPoints(player))
        }

        get("/{id}") {
            val id = call.parameters["id"]
            call.respond(playerService.getPlayer(id!!))
        }

        get {
            call.respond(playerService.getSortedPlayers())
        }

        delete {
            playerService.deleteAllPlayers()
            call.respond(NoContent)
        }
    }

}
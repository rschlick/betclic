package com.betclic.service

import com.betclic.domain.Player
import com.betclic.repository.PlayerRepository

class PlayerService(private val playerRepository: PlayerRepository) {

    suspend fun addPlayer(player: Player): Player {
        playerRepository.addPlayer(player).apply { recalculatePlayersRanking() }
        return getPlayer(player.id!!)
    }


    suspend fun updatePlayerPoints(player: Player): Player {
        playerRepository.updatePlayerPoints(player).apply { recalculatePlayersRanking() }
        return getPlayer(player.id!!)
    }

    suspend fun getPlayer(id: String): Player = playerRepository.getPlayer(id)

    suspend fun getSortedPlayers(): List<Player> = playerRepository.getSortedPlayers()

    suspend fun deleteAllPlayers() = playerRepository.deleteAllPlayers()

    private suspend fun recalculatePlayersRanking() {
        var ranking = 1
        getSortedPlayers()
            .forEach {
                it.ranking = ranking++
                playerRepository.updatePlayerRanking(it)
            }
    }
}

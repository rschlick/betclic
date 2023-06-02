package com.betclic.repository

import com.betclic.domain.Player
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.*

class PlayerRepository(database: MongoDatabase) {
    private val collection = database.getCollection<Player>()

    suspend fun addPlayer(player: Player): Player {
        collection.insertOne(player)
        return getPlayer(player.id!!)
    }

    suspend fun updatePlayerPoints(player: Player) {
        collection.updateOne(Player::id eq player.id, setValue(Player::points, player.points))
    }

    suspend fun updatePlayerRanking(player: Player) {
        collection.updateOne(Player::id eq player.id, setValue(Player::ranking, player.ranking))
    }

    suspend fun getPlayer(id: String): Player {
        return collection.findOne(Player::id eq id) ?: throw Exception("Player not found")
    }

    suspend fun getSortedPlayers(): List<Player> {
        return collection.find().sort(descending(Player::points)).toList()
    }

    suspend fun deleteAllPlayers() {
        collection.deleteMany()
    }
}

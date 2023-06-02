package com.betclic

import com.betclic.domain.Player
import com.betclic.repository.PlayerRepository
import com.betclic.service.PlayerService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class PlayerServiceTest {

    private lateinit var playerService: PlayerService
    private val playerRepository = mockk<PlayerRepository>(relaxed = true)
    private val player1 = Player("1", "player1", 10, 1)
    private val player2 = Player("2", "player2", 5, 2)
    private val players = listOf(player1, player2)

    @BeforeTest
    fun setUp() {
        playerService = PlayerService(playerRepository)
    }

    @Test
    fun `addPlayer calls repository and returns player`() = runBlocking {
        //Given
        coEvery { playerRepository.addPlayer(any()) } returns player1
        coEvery { playerRepository.getSortedPlayers() } returns listOf(player1, player2)
        coEvery { playerRepository.getPlayer(player1.id!!) } returns player1

        //When
        val result = playerService.addPlayer(player1)

        //Then
        coVerify { playerRepository.addPlayer(player1) }
        assertEquals(player1, result)
    }

    @Test
    fun `addPlayer calls repository and recalculate ranking`() = runBlocking {

        //Given
        val player3 = Player("3", "player3", 500, 2)
        coEvery { playerRepository.addPlayer(player3) } returns player3
        coEvery { playerRepository.getSortedPlayers() } returns listOf(player3, player1, player2)
        coEvery { playerRepository.getPlayer(player3.id!!) } returns player3

        //When
        val addedPlayer3 = playerService.addPlayer(player3)

        //Then
        assertEquals(1, addedPlayer3.ranking)
        player3.ranking = 1
        player1.ranking = 2
        player2.ranking = 3
        coVerify { playerRepository.updatePlayerRanking(player1) }
        coVerify { playerRepository.updatePlayerRanking(player2) }
        coVerify { playerRepository.updatePlayerRanking(player3) }

    }

    @Test
    fun `updatePlayerPoints calls repository and returns player`() = runBlocking {
        //Given
        coEvery { playerRepository.updatePlayerPoints(any()) } returns Unit
        coEvery { playerRepository.getSortedPlayers() } returns listOf(player1, player2)
        coEvery { playerRepository.getPlayer(player1.id!!) } returns player1

        //When
        val result = playerService.updatePlayerPoints(player1)

        //Then
        coVerify { playerRepository.updatePlayerPoints(player1) }
        assertEquals(player1, result)
    }

    @Test
    fun `getPlayer calls repository and returns player`() = runBlocking {
        //Given
        coEvery { playerRepository.getPlayer(any()) } returns player1

        //When
        val result = playerService.getPlayer("1")

        //Then
        coVerify { playerRepository.getPlayer("1") }
        assertEquals(player1, result)
    }

    @Test
    fun `getSortedPlayers calls repository and returns sorted players`() = runBlocking {
        //Given
        coEvery { playerRepository.getSortedPlayers() } returns players

        //When
        val result = playerService.getSortedPlayers()

        //Then
        coVerify { playerRepository.getSortedPlayers() }
        assertEquals(players, result)
    }

    @Test
    fun `deleteAllPlayers calls repository`() = runBlocking {
        //Given
        coEvery { playerRepository.deleteAllPlayers() } returns Unit

        //When
        playerService.deleteAllPlayers()

        //Then
        coVerify { playerRepository.deleteAllPlayers() }
    }
}

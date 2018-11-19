package org.ventura.tictactoe.application.players;

import org.junit.jupiter.api.Test;
import org.ventura.tictactoe.application.GameConfigurationDefaultCharactersSize3Stub;
import org.ventura.tictactoe.domain.player.Player;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

class GamePlayersTest {

    @Test
    void playersOrderShouldBeDecidedBySorter() {
        GamePlayers gamePlayers = new GamePlayers(new GameConfigurationDefaultCharactersSize3Stub(), playerCharacterAscendingOrder());

        assertThat(gamePlayers.currentPlayer().getPlayerCharacter()).isEqualTo("A");
        assertNextPlayer("O", gamePlayers);
        assertNextPlayer("X", gamePlayers);
    }

    @Test
    void shouldGetNextPlayerInOrder() {
        GamePlayers gamePlayers = new GamePlayers(new GameConfigurationDefaultCharactersSize3Stub(), dummyPlayersSorter());

        assertNextPlayer("O", gamePlayers);
        assertNextPlayer("A", gamePlayers);
        assertNextPlayer("X", gamePlayers);
    }

    @Test
    void shouldRetrieveCurrentPlayerBeforeAskForNext() {
        GamePlayers gamePlayers = new GamePlayers(new GameConfigurationDefaultCharactersSize3Stub(), dummyPlayersSorter());

        assertThat(gamePlayers.currentPlayer().getPlayerCharacter()).isEqualTo("X");
    }

    private void assertNextPlayer(String character, GamePlayers gamePlayers) {
        gamePlayers.nextPlayer();
        assertThat(gamePlayers.currentPlayer().getPlayerCharacter()).isEqualTo(character);
    }

    private PlayersSorter dummyPlayersSorter() {
        return players -> {
        };
    }

    private PlayersSorter playerCharacterAscendingOrder() {
        return players -> {
            players.sort(Comparator.comparing(Player::getPlayerCharacter));
        };
    }
}
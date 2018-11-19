package org.ventura.tictactoe.application.players;

import org.ventura.tictactoe.domain.player.AI;
import org.ventura.tictactoe.domain.player.Player;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class GamePlayers {

    private Deque<Player> players;

    public GamePlayers(PlayersConfiguration configuration, PlayersSorter playersSorter) {
        List<Player> players = buildPlayers(configuration);
        playersSorter.sort(players);
        this.players = new LinkedList<>(players);
    }

    private List<Player> buildPlayers(PlayersConfiguration playersConfiguration) {
        Player player1 = new Player(playersConfiguration.characterForPlayer1());
        Player player2 = new Player(playersConfiguration.characterForPlayer2());
        AI ai = new AI(playersConfiguration.characterForAI());

        return asList(player1, player2, ai);
    }

    public void nextPlayer() {
        Player nextPlayer = this.players.pop();
        this.players.addLast(nextPlayer);
    }

    public Player currentPlayer() {
        return this.players.getFirst();
    }

    public Player lastReturnedPlayer() {
        return this.players.getLast();
    }
}

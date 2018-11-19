package org.ventura.tictactoe.application.players;

import org.ventura.tictactoe.domain.player.Player;

import java.util.List;

public interface PlayersSorter {

    void sort(List<Player> players);
}

package org.ventura.tictactoe.application.players;

import org.ventura.tictactoe.domain.player.Player;

import java.util.Collections;
import java.util.List;

public class RandomPlayersSorter implements PlayersSorter {

    @Override
    public void sort(List<Player> players) {
        Collections.shuffle(players);
    }
}

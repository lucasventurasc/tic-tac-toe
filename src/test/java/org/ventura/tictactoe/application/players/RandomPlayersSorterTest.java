package org.ventura.tictactoe.application.players;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ventura.tictactoe.domain.player.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class RandomPlayersSorterTest {

    private RandomPlayersSorter randomPlayersSorter;

    @BeforeEach
    void setup() {
        randomPlayersSorter = new RandomPlayersSorter();
    }

    @Test
    void shouldSortPlayersInARandomOrder() {
        List<Player> playersInOrder = asList(new Player("A"), new Player("B"), new Player("C"));

        int countDifferentOrderTimes = countHowManyTimesPlayersHasADifferentOrderAfterSort(playersInOrder);

        assertThat(countDifferentOrderTimes).isGreaterThan(2);
    }

    private int countHowManyTimesPlayersHasADifferentOrderAfterSort(List<Player> playersInOrder) {
        int countDifferentOrderTimes = 0;
        for (int i = 0; i < 10; i++) {
            List<Player> playersToSort = new CopyOnWriteArrayList<>(playersInOrder);
            randomPlayersSorter.sort(playersToSort);

            if (hasDifferentOrder(playersInOrder, playersToSort)) {
                countDifferentOrderTimes += 1;
            }
        }
        return countDifferentOrderTimes;
    }

    private boolean hasDifferentOrder(List<Player> playersInOrder, List<Player> playersToSort) {
        return !playersToSort.equals(playersInOrder);
    }

}
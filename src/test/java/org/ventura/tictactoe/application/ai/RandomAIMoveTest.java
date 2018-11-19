package org.ventura.tictactoe.application.ai;

import org.junit.jupiter.api.Test;
import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.move.Move;

class RandomAIMoveTest {

    @Test
    void shouldNeverReturnAMoveForAnAlreadyFilledPosition() {
        Field field = new Field(3);
        RandomAIMove randomAIMove = new RandomAIMove();


        for (int i = 0; i < 9; i++) {
            Move move = randomAIMove.nextMove(field);
            field.move(move,"A");
        }
    }
}
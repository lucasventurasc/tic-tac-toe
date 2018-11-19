package org.ventura.tictactoe.application.ai;

import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.move.Move;

import java.util.Random;

public class RandomAIMove implements AIMove {

    public RandomAIMove() {
    }

    public Move nextMove(Field field) {
        return generateMove(field);
    }

    private Move generateMove(Field field) {
        Random random = new Random();
        int x = random.nextInt(field.size());
        int y = random.nextInt(field.size());

        Move move = new Move(x, y);
        return field.position(move.x(), move.y()).isPresent() ? generateMove(field) : move;
    }
}

package org.ventura.tictactoe.application.ai;

import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.move.Move;

public interface AIMove {

    Move nextMove(Field field);
}

package org.ventura.tictactoe.application.game;

import org.ventura.tictactoe.domain.gamefield.FieldState;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.domain.player.Player;

public interface UserInteraction {

    void updateField(FieldState fieldState);
    Move waitForMoveFrom(Player player);
    void showMessage(String message);
}

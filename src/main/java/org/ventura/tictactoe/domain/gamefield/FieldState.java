package org.ventura.tictactoe.domain.gamefield;

import java.util.Optional;

public interface FieldState {

    Optional<FieldPosition> position(int x, int y);
    int size();
}

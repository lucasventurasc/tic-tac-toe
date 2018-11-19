package org.ventura.tictactoe.application;

import org.ventura.tictactoe.application.game.FieldConfiguration;
import org.ventura.tictactoe.application.players.PlayersConfiguration;

public class GameConfigurationDefaultCharactersSize3Stub implements FieldConfiguration, PlayersConfiguration {
    @Override
    public String characterForPlayer1() {
        return "X";
    }

    @Override
    public String characterForPlayer2() {
        return "O";
    }

    @Override
    public String characterForAI() {
        return "A";
    }

    @Override
    public int fieldSize() {
        return 3;
    }
}

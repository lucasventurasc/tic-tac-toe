package org.ventura.tictactoe.application.game;

import org.ventura.tictactoe.application.players.PlayersConfiguration;

public interface GameConfiguration {

    FieldConfiguration getFieldConfiguration();

    PlayersConfiguration getPlayersConfiguration();
}

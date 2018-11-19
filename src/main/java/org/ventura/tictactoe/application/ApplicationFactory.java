package org.ventura.tictactoe.application;

import org.ventura.tictactoe.application.ai.AIMove;
import org.ventura.tictactoe.application.game.Game;
import org.ventura.tictactoe.application.game.GameConfiguration;
import org.ventura.tictactoe.application.game.GameDependencies;
import org.ventura.tictactoe.application.players.PlayersSorter;
import org.ventura.tictactoe.infrastructure.console.userinterface.ConsoleUserInteraction;

public interface ApplicationFactory {

    AIMove aiMove();

    ConsoleUserInteraction consoleUserInteraction();

    PlayersSorter playersSorter();

    GameConfiguration gameConfiguration();

    default Game game() {
        GameDependencies gameDependencies = new GameDependencies();
        gameDependencies.setAiMove(aiMove());
        gameDependencies.setUserInteraction(consoleUserInteraction());
        gameDependencies.setPlayersSorter(playersSorter());
        gameDependencies.setGameConfiguration(gameConfiguration());
        return new Game(gameDependencies);
    }
}

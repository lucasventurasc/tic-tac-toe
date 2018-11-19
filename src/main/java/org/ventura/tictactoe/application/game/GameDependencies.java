package org.ventura.tictactoe.application.game;

import org.ventura.tictactoe.application.ai.AIMove;
import org.ventura.tictactoe.application.players.PlayersSorter;

public class GameDependencies {

    private UserInteraction userInteraction;
    private AIMove aiMove;
    private PlayersSorter playersSorter;
    private GameConfiguration gameConfiguration;

    public GameDependencies() {
    }

    public void setUserInteraction(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }

    public void setAiMove(AIMove aiMove) {
        this.aiMove = aiMove;
    }

    public void setPlayersSorter(PlayersSorter playersSorter) {
        this.playersSorter = playersSorter;
    }

    public void setGameConfiguration(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
    }

    public UserInteraction getUserInteraction() {
        return userInteraction;
    }

    public AIMove getAiMove() {
        return aiMove;
    }

    public PlayersSorter getPlayersSorter() {
        return playersSorter;
    }

    public GameConfiguration getGameConfiguration() {
        return gameConfiguration;
    }
}

package org.ventura.tictactoe.application.game;

import org.ventura.tictactoe.application.ai.AIMove;
import org.ventura.tictactoe.application.players.GamePlayers;
import org.ventura.tictactoe.application.players.PlayersSorter;
import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.gamefield.GameResultChecker;
import org.ventura.tictactoe.domain.gamefield.PositionAlreadyFilledException;
import org.ventura.tictactoe.domain.gamefield.PositionOutOfRangeException;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.domain.move.MoveResult;
import org.ventura.tictactoe.domain.player.AI;
import org.ventura.tictactoe.domain.player.Player;

public class Game {

    private UserInteraction userInteraction;
    private GameResultChecker gameResultChecker;
    private AIMove aiMove;
    private PlayersSorter playersSorter;
    private GameConfiguration gameConfiguration;

    public Game(GameDependencies gameDependencies) {
        this.userInteraction = gameDependencies.getUserInteraction();
        this.aiMove = gameDependencies.getAiMove();
        this.playersSorter = gameDependencies.getPlayersSorter();
        this.gameConfiguration = gameDependencies.getGameConfiguration();
        this.gameResultChecker = new GameResultChecker();
    }

    public void start() {
        GamePlayers gamePlayers = new GamePlayers(gameConfiguration.getPlayersConfiguration(), playersSorter);
        Field field = new Field(gameConfiguration.getFieldConfiguration().fieldSize());

        MoveResult result = MoveResult.NO_RESULT;
        while (result == MoveResult.NO_RESULT) {
            Move move = nextMove(gamePlayers, field);
            result = applyMove(move, gamePlayers, field);
        }

        finish(result, gamePlayers.lastReturnedPlayer());
    }

    private Move nextMove(GamePlayers gamePlayers, Field field) {
        Player player = gamePlayers.currentPlayer();
        if (player instanceof AI) {
            userInteraction.showMessage("Now it's AI's turn");
            return aiMove.nextMove(field);
        }
        return userInteraction.waitForMoveFrom(player);
    }

    private MoveResult applyMove(Move move, GamePlayers gamePlayers, Field field) {
        try {
            field.move(move, gamePlayers.currentPlayer().getPlayerCharacter());
            gamePlayers.nextPlayer();
            userInteraction.updateField(field);
            return gameResultChecker.check(field);
        } catch (PositionAlreadyFilledException | PositionOutOfRangeException ex) {
            userInteraction.showMessage(ex.getMessage());
            return MoveResult.NO_RESULT;
        }
    }

    private void finish(MoveResult result, Player winner) {
        if (result == MoveResult.WINNER) {
            userInteraction.showMessage(winner.getPlayerName() + " is the WINNER");
        } else {
            userInteraction.showMessage("Game DRAW");
        }
    }
}

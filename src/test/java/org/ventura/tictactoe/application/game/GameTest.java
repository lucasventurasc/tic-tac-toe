package org.ventura.tictactoe.application.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.ventura.tictactoe.application.GameConfigurationDefaultCharactersSize3Stub;
import org.ventura.tictactoe.application.ai.AIMove;
import org.ventura.tictactoe.application.players.PlayersConfiguration;
import org.ventura.tictactoe.application.players.PlayersSorter;
import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.gamefield.FieldState;
import org.ventura.tictactoe.domain.gamefield.PositionAlreadyFilledException;
import org.ventura.tictactoe.domain.gamefield.PositionOutOfRangeException;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.domain.player.AI;
import org.ventura.tictactoe.domain.player.Player;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.*;
import static org.mockito.internal.stubbing.answers.DoesNothing.doesNothing;

@ExtendWith(MockitoExtension.class)
class GameTest {

    @Mock
    private UserInteraction userInteraction;

    @Mock
    private AIMove aiMove;

    @Mock
    private PlayersSorter playersSorterDummy;

    private static final Player PLAYER_X = new Player("X");
    private static final Player PLAYER_O = new Player("O");
    private static final AI PLAYER_A = new AI("A");

    private Game game;
    private GameConfiguration gameConfiguration;
    
    @BeforeEach
    void setup() {
        userInteraction = mock(UserInteraction.class);
        gameConfiguration = gameConfigurationCustomFieldSize(3);
        game = new Game(getGameDependencies());
    }

    private GameDependencies getGameDependencies() {
        GameDependencies gameDependencies = new GameDependencies();
        gameDependencies.setUserInteraction(userInteraction);
        gameDependencies.setGameConfiguration(gameConfiguration);
        gameDependencies.setAiMove(aiMove);
        gameDependencies.setPlayersSorter(playersSorterDummy);
        return gameDependencies;
    }

    /*_x_|_x_|_x_
      _o_|_a_|_o_
         |   | a */
    @Test
    void shouldAskUsersUntilSomePlayerWin() {
        when(userInteraction.waitForMoveFrom(anyPlayer())).thenReturn(
                new Move(0, 0), new Move(1, 0),
                new Move(0, 1), new Move(1, 2),
                new Move(0, 2)
        );

        when(aiMove.nextMove(anyFieldState())).thenReturn(
                new Move(1, 1), new Move(2, 2)
        );

        game.start();

        assertWinner(PLAYER_X);
    }

    /*_x_|_o_|_a_
      _x_|_o_|_a_
      _a_| x | o*/
    @Test
    void shouldAskUsersUntilDrawAndApplyMovesToTheField() {
        when(userInteraction.waitForMoveFrom(anyPlayer())).thenReturn(
                new Move(0, 0), new Move(0, 1),
                new Move(1, 0), new Move(1, 1),
                new Move(2, 1), new Move(2, 2)
        );

        when(aiMove.nextMove(anyFieldState())).thenReturn(
                new Move(0, 2), new Move(1, 2), new Move(2, 0)
        );

        game.start();

        assertGameDraw();
    }


    /*_x_|_o_|_a_
      _x_|_o_|_a_
      _o_| x | a*/
    @Test
    void shouldNotBeDrawWhenLastMovementWinsTheGame() {
        when(userInteraction.waitForMoveFrom(anyPlayer())).thenReturn(
                new Move(0, 0), new Move(0, 1),
                new Move(1, 0), new Move(1, 1),
                new Move(2, 1), new Move(2, 0)
        );

        when(aiMove.nextMove(anyFieldState())).thenReturn(
                new Move(0, 2), new Move(1, 2), new Move(2, 2)
        );
        game.start();

        assertWinner(PLAYER_A);
    }

    /*_x_|_a_|_o_
      _x_|_x_|_o_
      _a_| a | o*/
    @Test
    void shouldAskPositionAgainWhenSelectedPositionIsAlreadyFilled() {
        Move playerO_WrongMove = new Move(0, 0);
        when(userInteraction.waitForMoveFrom(anyPlayer())).thenReturn(
                new Move(0, 0),
                playerO_WrongMove,
                playerO_WrongMove,
                new Move(0, 2),

                new Move(1, 0),
                new Move(1, 2),

                new Move(1, 1),
                new Move(2, 2)
        );

        when(aiMove.nextMove(anyFieldState())).thenReturn(
                new Move(0, 1), new Move(2, 0), new Move(2, 1)
        );

        game.start();

        verify(userInteraction, twice()).showMessage(new PositionAlreadyFilledException().getMessage());
        assertWinner(PLAYER_O);
    }

    /*_x_|_o_|_a_
      _o_|_x_|_a_
       o | a | x */
    @Test
    void shouldAskPositionAgainWhenSelectedPositionIsOutsideOfRange() {
        Move firstMovePlayerO_outOfRange = new Move(10, 10);
        Move secondMovePlayerO_outOfRange = new Move(10, 10);

        when(userInteraction.waitForMoveFrom(anyPlayer())).thenReturn(
                new Move(0, 0),
                firstMovePlayerO_outOfRange,
                secondMovePlayerO_outOfRange,
                new Move(0, 1),

                new Move(1, 1),
                new Move(1, 0),

                new Move(2, 2),
                new Move(2, 0)
        );

        when(aiMove.nextMove(anyFieldState())).thenReturn(
                new Move(0, 2), new Move(2, 1), new Move(1, 2)
        );

        game.start();

        verify(userInteraction, twice()).showMessage(new PositionOutOfRangeException().getMessage());
        assertWinner(PLAYER_X);
    }

    /*_x_|_o_|___|_a_
      _x_|___|___|_a_
      _x_|_o_|___|_o_
      _x_| a |   |  */
    @Test
    void shouldSupportDifferentSizesOfFields() {
        GameDependencies gameDependencies = getGameDependencies();
        gameDependencies.setGameConfiguration(gameConfigurationCustomFieldSize(4));
        Game game = new Game(gameDependencies);

        when(userInteraction.waitForMoveFrom(anyPlayer())).thenReturn(
                new Move(0, 0), new Move(0, 1),
                new Move(1, 0), new Move(2, 3),
                new Move(2, 0), new Move(2, 1),
                new Move(3, 0)
        );

        when(aiMove.nextMove(anyFieldState())).thenReturn(
                new Move(0, 3), new Move(1, 3), new Move(3, 1)
        );

        game.start();

        assertWinner(PLAYER_X);
    }

    private GameConfiguration gameConfigurationCustomFieldSize(int customFieldSize) {
        return new GameConfiguration() {
            @Override
            public FieldConfiguration getFieldConfiguration() {
                return () -> customFieldSize;
            }

            @Override
            public PlayersConfiguration getPlayersConfiguration() {
                return new GameConfigurationDefaultCharactersSize3Stub();
            }
        };
    }

    @Test
    void shouldMessageUserThatIsAITurnInItsTurn() {
        when(userInteraction.waitForMoveFrom(anyPlayer())).
        thenReturn(new Move(0, 0), new Move(1, 0), new Move(0, 1),
                   new Move(1, 2), new Move(0, 2));

        when(aiMove.nextMove(anyFieldState())).thenReturn(new Move(1, 1), new Move(2, 2));

        game.start();

        InOrder inOrder = inOrder(userInteraction, aiMove);
        inOrder.verify(userInteraction).showMessage("Now it's AI's turn");
        inOrder.verify(aiMove).nextMove(anyFieldState());

        assertWinner(PLAYER_X);
    }

    @Test
    void shouldUpdateUserFieldAfterEachSuccessfulMove() {
        Move firstMove = new Move(0, 0);
        Move secondMove = new Move(1, 0);
        Move thirdMove = new Move(1, 1);
        Move fourthMove = new Move(0, 1);
        Move fifthMove = new Move(1, 2);
        Move sixthMove = new Move(2, 2);
        Move lastMove = new Move(0, 2);

        List<Move> movesInOrder = asList(firstMove, secondMove, thirdMove, fourthMove, fifthMove, sixthMove, lastMove);

        when(userInteraction.waitForMoveFrom(anyPlayer())).thenReturn(firstMove, secondMove, fourthMove, fifthMove, lastMove);
        when(aiMove.nextMove(anyFieldState())).thenReturn(thirdMove, sixthMove);
        ArgumentCaptor<FieldState> fieldStatesCaptor = captureAllFieldStateUpdates(userInteraction);

        game.start();

        assertThatMovesMatchesFieldStatesInOrder(movesInOrder, fieldStatesCaptor.getAllValues());
    }

    private void assertThatMovesMatchesFieldStatesInOrder(List<Move> movesInOrder, List<FieldState> fieldStateHistory) {
        for (int moveNumber = 0; moveNumber < movesInOrder.size(); moveNumber++) {
            Move move = movesInOrder.get(moveNumber);
            FieldState fieldState = fieldStateHistory.get(moveNumber);
            assertThat(fieldState.position(move.x(), move.y())).isPresent();
        }
    }
    private ArgumentCaptor<FieldState> captureAllFieldStateUpdates(UserInteraction userInteraction) {
        ArgumentCaptor<FieldState> captor = ArgumentCaptor.forClass(FieldState.class);
        doAnswer(doesNothing()).when(userInteraction).updateField(captor.capture());
        return captor;
    }

    private VerificationMode twice() {
        return times(2);
    }

    private VerificationMode once() {
        return times(1);
    }

    private Player anyPlayer() {
        return any(Player.class);
    }

    private Field anyFieldState() {
        return any(Field.class);
    }

    private void assertWinner(Player currentPlayer) {
        verify(userInteraction, once()).showMessage(matches("(.*" + currentPlayer.getPlayerName() + ".*WINNER.*)"));
    }

    private void assertGameDraw() {
        verify(userInteraction, once()).showMessage(matches("(.*DRAW.*)"));
    }
}
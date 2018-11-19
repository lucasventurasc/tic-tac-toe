package org.ventura.tictactoe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ventura.tictactoe.application.ai.AIMove;
import org.ventura.tictactoe.application.players.PlayersSorter;
import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.infrastructure.console.userinterface.ScannerWrapper;

import java.io.PrintStream;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicTacToeTest {

    @Mock
    private ScannerWrapper scannerWrapperMock;

    @Mock
    private PrintStream printStreamMock;

    @Mock
    private AIMove aiMoveMock;

    /*
      _x_|_o_|_a_
      _x_|_o_|_a_
       x |   |
     */
    @Test
    void smokeTestWin() {
        TicTacToe.applicationFactory = new ConsoleApplicationFactoryForTest();

        when(scannerWrapperMock.nextLine()).thenReturn(
                getAbsolutePathFor("game_configuration_test1.properties"),
                move(0, 0), move(0, 1),
                move(1, 0), move(1, 1),
                move(2, 0)
        );

        when(aiMoveMock.nextMove(field())).thenReturn(
                new Move(0, 2), new Move(1, 2)
        );

        assertTimeoutPreemptively(ofMillis(500), () -> {
            TicTacToe.main(null);
            verify(printStreamMock, times(1)).println("Player X is the WINNER");
        });
    }

    /*
      _x_|_o_|_a_
      _x_|_o_|_a_
       o | a | x
     */
    @Test
    void smokeTestDraw() {
        TicTacToe.applicationFactory = new ConsoleApplicationFactoryForTest();

        when(scannerWrapperMock.nextLine()).thenReturn(
                getAbsolutePathFor("game_configuration_test1.properties"),
                move(0, 0), move(0, 1),
                move(1, 0), move(1, 1),
                move(2, 2), move(2, 0)
        );

        when(aiMoveMock.nextMove(field())).thenReturn(
                new Move(0, 2), new Move(2, 1), new Move(1, 2)
        );

        assertTimeoutPreemptively(ofMillis(500), () -> {
            TicTacToe.main(null);

            verify(printStreamMock, times(1)).println("Game DRAW");
        });
    }

    @Test
    void smokeTestFailConfigurationFile() {
        TicTacToe.applicationFactory = new ConsoleApplicationFactoryForTest();

        when(scannerWrapperMock.nextLine()).thenReturn(
            getAbsolutePathFor("game_configuration_test8.properties")
        );

        assertTimeoutPreemptively(ofMillis(500), () -> {
            TicTacToe.main(null);

            verify(printStreamMock, times(1)).
            println("Please type the path for your configuration file:");

            verify(printStreamMock, times(1)).
            println("Invalid game configuration: Property player1.character should have length 1");
        });
    }

    private Field field() {
        return any(Field.class);
    }

    private String move(int x, int y) {
        return x + "," + y;
    }

    private String getAbsolutePathFor(String file) {
        return getClass().getClassLoader().getResource(file).getPath().replace("%20", " ");
    }

    private PlayersSorter dummyPlayersSorter() {
        return players -> {
        };
    }

    class ConsoleApplicationFactoryForTest extends ConsoleApplicationFactory {

        @Override
        public AIMove aiMove() {
            return aiMoveMock;
        }

        @Override
        public PlayersSorter playersSorter() {
            return dummyPlayersSorter();
        }

        @Override
        ScannerWrapper scannerWrapper() {
            return scannerWrapperMock;
        }

        @Override
        PrintStream printStream() {
            return printStreamMock;
        }
    }
}

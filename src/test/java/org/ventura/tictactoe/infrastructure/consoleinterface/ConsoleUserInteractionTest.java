package org.ventura.tictactoe.infrastructure.consoleinterface;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.verification.VerificationMode;
import org.ventura.tictactoe.domain.gamefield.FieldPosition;
import org.ventura.tictactoe.domain.gamefield.FieldState;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.domain.player.Player;
import org.ventura.tictactoe.infrastructure.console.userinterface.ConsoleUserInputReader;
import org.ventura.tictactoe.infrastructure.console.userinterface.ConsoleUserInteraction;
import org.ventura.tictactoe.infrastructure.console.userinterface.InvalidInputException;

import java.io.PrintStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConsoleUserInteractionTest {

    @Mock
    private PrintStream printStream;

    @Mock
    private ConsoleUserInputReader consoleUserInputReader;

    @InjectMocks
    private ConsoleUserInteraction consoleUserInteraction;

    @Test
    void shouldMessagePlayerWhenIsItsTurnBeforeAskTheInput() {
        InOrder inOrder = inOrder(printStream, consoleUserInputReader);

        Player player = new Player("Z");
        consoleUserInteraction.waitForMoveFrom(player);

        inOrder.verify(printStream, once()).println("Player Z: your turn, type x,y");
        inOrder.verify(consoleUserInputReader, once()).read();
    }

    @Test
    void shouldAskAgainWhenUserInputInvalidData() {
        Player playerX = new Player("X");

        Move expectedUserInput = new Move(0, 0);
        when(consoleUserInputReader.read()).thenThrow(new InvalidInputException())
                                           .thenReturn(expectedUserInput);
        Move move = consoleUserInteraction.waitForMoveFrom(playerX);
        verify(printStream, twice()).println("Player X: your turn, type x,y");
        verify(printStream, once()).println("Available inputs are integers inside of field range 'x,y'");

        assertThat(move).isEqualToComparingFieldByField(expectedUserInput);
    }

    @Test
    void shouldAcceptIntWithTwoDigits() {
        Move expectedUserInput = new Move(10, 10);
        when(consoleUserInputReader.read()).thenReturn(expectedUserInput);

        Player player = new Player("X");

        Move move = consoleUserInteraction.waitForMoveFrom(player);

        assertThat(move).isEqualToComparingFieldByField(expectedUserInput);
    }

    @Test
    void shouldPrintFieldOnUpdateField() {
        FieldState fieldState = mock(FieldState.class);
        when(fieldState.size()).thenReturn(3);
        when(fieldState.position(anyInt(), anyInt())).thenReturn(Optional.empty());

        consoleUserInteraction.updateField(fieldState);

        verify(printStream, once()).println(
                "___|___|___\n" +
                "___|___|___\n" +
                "   |   |   "
        );
    }

    @Test
    void shouldPrintFilledFieldOnUpdateField() {
        FieldState fieldState = mock(FieldState.class);
        when(fieldState.size()).thenReturn(3);
        when(fieldState.position(anyInt(), anyInt())).thenReturn(Optional.empty());


        FieldPosition k_fieldPosition = createFieldPositionMock(0, 0, "K");
        when(fieldState.position(0, 0)).thenReturn(Optional.of(k_fieldPosition));

        FieldPosition x_fieldPosition = createFieldPositionMock(2, 2, "X");
        when(fieldState.position(2, 2)).thenReturn(Optional.of(x_fieldPosition));

        consoleUserInteraction.updateField(fieldState);
        verify(printStream, once()).println(
                "_K_|___|___\n" +
                "___|___|___\n" +
                "   |   | X "
        );
    }

    private FieldPosition createFieldPositionMock(int x, int y, String characterFilling) {
        FieldPosition fieldPosition = mock(FieldPosition.class);
        when(fieldPosition.row()).thenReturn(x);
        when(fieldPosition.column()).thenReturn(y);
        when(fieldPosition.playerCharacter()).thenReturn(characterFilling);
        return fieldPosition;
    }

    private VerificationMode twice() {
        return times(2);
    }

    private VerificationMode once() {
        return times(1);
    }
}

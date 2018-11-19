package org.ventura.tictactoe.infrastructure.consoleinterface;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.infrastructure.console.userinterface.ConsoleUserInputReader;
import org.ventura.tictactoe.infrastructure.console.userinterface.InvalidInputException;
import org.ventura.tictactoe.infrastructure.console.userinterface.ScannerWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsoleUserInputReaderTest {

    @Mock
    private ScannerWrapper scannerWrapper;

    @InjectMocks
    private ConsoleUserInputReader consoleUserInputReader;

    @Test
    void shouldNotAcceptNonIntCharacters_x() {
        when(scannerWrapper.nextLine()).thenReturn("p,9");
        when(scannerWrapper.nextLine()).thenReturn("p,9");

        assertThrows(InvalidInputException.class, () -> consoleUserInputReader.read());
    }

    @Test
    void shouldHandleWhenUserNotInputComma() {
        when(scannerWrapper.nextLine()).thenReturn("99");

        assertThrows(InvalidInputException.class, () -> consoleUserInputReader.read());
    }

    @Test
    void shouldHandleInputWhenUserNotInputTheY() {
        when(scannerWrapper.nextLine()).thenReturn("1,");

        assertThrows(InvalidInputException.class, () -> consoleUserInputReader.read());
    }

    @Test
    void shouldHandleInputWhenUserNotInputTheX() {
        when(scannerWrapper.nextLine()).thenReturn(",1");

        assertThrows(InvalidInputException.class, () -> consoleUserInputReader.read());
    }

    @Test
    void shouldNotAcceptNonIntCharacters_y() {
        when(scannerWrapper.nextLine()).thenReturn("9,p");

        assertThrows(InvalidInputException.class, () -> consoleUserInputReader.read());
    }

    @Test
    void shouldNotAcceptNonIntCharacters_x_y() {
        when(scannerWrapper.nextLine()).thenReturn("k,i");

        assertThrows(InvalidInputException.class, () -> consoleUserInputReader.read());
    }

    @Test
    void shouldReadUserInputAndCreateMove() {
        when(scannerWrapper.nextLine()).thenReturn("3,2");

        Move move = consoleUserInputReader.read();

        assertThat(move.x()).isEqualTo(3);
        assertThat(move.y()).isEqualTo(2);
    }

}

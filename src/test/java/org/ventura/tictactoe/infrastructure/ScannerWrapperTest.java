package org.ventura.tictactoe.infrastructure;

import org.junit.jupiter.api.Test;
import org.ventura.tictactoe.infrastructure.console.userinterface.ScannerWrapper;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ScannerWrapperTest {

    @Test
    void shouldScanNextLineFromSystemIn() {
        System.setIn(new ByteArrayInputStream("scanner_line_here".getBytes()));
        String readLine = new ScannerWrapper().nextLine();

        assertThat(readLine).isEqualTo("scanner_line_here");
    }

}
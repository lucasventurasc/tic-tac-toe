package org.ventura.tictactoe.infrastructure.console.userinterface;

import java.util.Scanner;

public class ScannerWrapper {

    public ScannerWrapper() {
    }

    public String nextLine() {
        return new Scanner(System.in).next();
    }
}

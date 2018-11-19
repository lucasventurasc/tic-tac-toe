package org.ventura.tictactoe.domain.player;

public class AI extends Player {

    public AI(String playableCharacter) {
        super(playableCharacter);
    }

    @Override
    public String getPlayerName() {
        return "AI";
    }
}

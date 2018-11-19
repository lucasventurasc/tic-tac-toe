package org.ventura.tictactoe.domain.player;

public class Player {

    private String playerCharacter;

    public Player(String playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public String getPlayerCharacter() {
        return playerCharacter;
    }

    public String getPlayerName() {
        return "Player " + playerCharacter;
    }
}

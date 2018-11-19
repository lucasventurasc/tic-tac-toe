package org.ventura.tictactoe.domain.player;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AITest {

    @Test
    void shouldReturnWordAiInsteadOfPlayerAndPlayerCharacterAtName() {
        assertThat(new AI("T").getPlayerName()).isEqualTo("AI");
    }
}
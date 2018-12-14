package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Player;

/**
 *
 */
public class RankEvaluatorResult {

    private final Player lessHealthyPlayer;
    private final Player mostHealthyPlayer;

    public RankEvaluatorResult(Player noHealthyPlayer, Player healthyPlayer) {
        this.lessHealthyPlayer = noHealthyPlayer;
        this.mostHealthyPlayer = healthyPlayer;
    }

    public Player getLessHealthyPlayer() {
        return lessHealthyPlayer;
    }

    public Player getMostHealthyPlayer() {
        return mostHealthyPlayer;
    }
}

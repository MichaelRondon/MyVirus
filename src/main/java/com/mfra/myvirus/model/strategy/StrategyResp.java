package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.cards.Card;

/**
 *
 */
public class StrategyResp implements Comparable<StrategyResp>{

    private final Card card;
    private Rank target;
    private Organ targetOrgan;

    public StrategyResp(Card card, Rank target) {
        this.card = card;
        this.target = target;
    }

    public Card getCard() {
        return card;
    }

    public Rank getTarget() {
        return target;
    }

    public void setTargetOrgan(Organ targetOrgan) {
        this.targetOrgan = targetOrgan;
    }

    public Organ getTargetOrgan() {
        return targetOrgan;
    }

    @Override
    public int compareTo(StrategyResp t) {
        return this.target.compareTo(t.target);
    }
}

package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.cards.Card;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StrategyResp {

    private final Card card;
    private Rank target;
    private Organ targetOrgan;
    private List<Rank> targets =  new ArrayList<>();

    public StrategyResp(Card card, Rank target) {
        this.card = card;
        this.target = target;
    }
    
    public StrategyResp(Card card, List<Rank> targets) {
        this.card = card;
        this.targets = targets;
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

    public List<Rank> getTargets() {
        return targets;
    }
}

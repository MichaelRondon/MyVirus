package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Organ;

/**
 *
 */
public class TransplantEntry extends RankEvaluatorResult {

    private Organ lessHealthyOrgan;
    private Organ mostHealthyOrgan;

    public TransplantEntry(RankEvaluatorResult rankEvaluatorResult) {
        super(rankEvaluatorResult.getLessHealthyPlayer(),
                        rankEvaluatorResult.getMostHealthyPlayer());
    }

    public Organ getLessHealthyOrgan() {
        return lessHealthyOrgan;
    }

    public void setLessHealthyOrgan(Organ lessHealthyOrgan) {
        this.lessHealthyOrgan = lessHealthyOrgan;
    }

    public Organ getMostHealthyOrgan() {
        return mostHealthyOrgan;
    }

    public void setMostHealthyOrgan(Organ mostHealthyOrgan) {
        this.mostHealthyOrgan = mostHealthyOrgan;
    }

}

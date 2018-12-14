package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.State;
import java.util.Map;

/**
 *
 */
public class MedicalError implements Card {

    public void playCard(RankEvaluatorResult rankEvaluatorResult) {
        Player player1 = rankEvaluatorResult.getLessHealthyPlayer();
        Player player2 = rankEvaluatorResult.getMostHealthyPlayer();
        Map<Organ, State> bodyClon1 = player1.getBodyClon();
        Map<Organ, State> bodyClon2 = player2.getBodyClon();
        player1.setBody(bodyClon2);
        player2.setBody(bodyClon1);
    }

}

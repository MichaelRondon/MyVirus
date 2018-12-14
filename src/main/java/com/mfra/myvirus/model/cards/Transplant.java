package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.State;

/**
 *
 */
public class Transplant implements Card{
    
    public void playCard(TransplantEntry transplantEntry){
        Player player1 = (Player) transplantEntry.getLessHealthyPlayer();
        Player player2 = (Player) transplantEntry.getMostHealthyPlayer();
        Organ organ1 = transplantEntry.getLessHealthyOrgan();
        Organ organ2 = transplantEntry.getMostHealthyOrgan();
        State state1 = player1.getStateAndRemove(organ1);
        State state2 = player2.getStateAndRemove(organ2);
        player1.putOrgan(organ2, state2);
        player2.putOrgan(organ1, state1);
    }

}

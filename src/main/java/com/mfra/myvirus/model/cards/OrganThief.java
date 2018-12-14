package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.Organ;

/**
 *
 */
public class OrganThief implements Card {

    public void playCard(TransplantEntry transplantEntry) {
        Player currentPlayer = (Player) transplantEntry.getLessHealthyPlayer();
        Player originPlayer = (Player) transplantEntry.getMostHealthyPlayer();
        Organ organ = transplantEntry.getMostHealthyOrgan();
        currentPlayer.putOrgan(organ, originPlayer.getStateAndRemove(organ));
    }
}

package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.OrganCardType;

/**
 *
 */
public class MultiOrganCard implements OrganCardType{

    @Override
    public void playCard(Player currentPlayer, Organ organ){
        currentPlayer.putOrgan(organ);
    }

    @Override
    public String toString() {
        return "MultiOrgan";
    }
}

package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.OrganCardType;

/**
 *
 */
public class MultiVirusCard implements OrganCardType{

    @Override
    public void playCard(Player targetPlayer, Organ organ){
        targetPlayer.sickOrgan(organ);
    }
}

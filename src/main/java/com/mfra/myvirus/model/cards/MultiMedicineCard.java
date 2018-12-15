package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.OrganCardType;

/**
 *
 */
public class MultiMedicineCard implements OrganCardType{

    @Override
    public void playCard(Player currentPlayer, Organ organ){
        currentPlayer.vacuneOrgan(organ);
    }

    @Override
    public String toString() {
        return "MultiMedicine";
    }
}

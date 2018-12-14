package com.mfra.myvirus.model;

import com.mfra.myvirus.model.cards.Card;

/**
 *
 */
public interface OrganCardType extends Card {

    public void playCard(Player player, Organ organ);

}

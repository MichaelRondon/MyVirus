package com.mfra.myvirus.model;

import com.mfra.myvirus.model.cards.Card;

/**
 *
 * @param <M>
 */
public interface SingleOrganCardType<M extends OrganCardType> extends Card{

    public Organ getOrgan();
    
    public M getMultiOrganCardType();
    
    default void playCard(Player player){
        getMultiOrganCardType().playCard(player, getOrgan());
    }
}

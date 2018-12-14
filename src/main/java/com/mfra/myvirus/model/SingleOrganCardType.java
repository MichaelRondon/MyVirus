package com.mfra.myvirus.model;

/**
 *
 * @param <M>
 */
public interface SingleOrganCardType<M extends OrganCardType> {

    public Organ getOrgan();
    
    public M getMultiOrganCardType();
    
    default void playCard(Player player){
        getMultiOrganCardType().playCard(player, getOrgan());
    }
}

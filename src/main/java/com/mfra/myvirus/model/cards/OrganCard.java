package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.SingleOrganCardType;

/**
 *
 */
public abstract class OrganCard implements SingleOrganCardType<MultiOrganCard>{
    
    private MultiOrganCard multiOrganCard = new MultiOrganCard();

    @Override
    public MultiOrganCard getMultiOrganCardType(){
        return multiOrganCard;
    }

    @Override
    public String toString() {
        return String.format("Organ: %s", this.getOrgan());
    }
}

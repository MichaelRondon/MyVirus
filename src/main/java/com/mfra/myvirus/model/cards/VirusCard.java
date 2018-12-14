package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.SingleOrganCardType;

/**
 *
 */
public abstract class VirusCard implements SingleOrganCardType<MultiVirusCard>{
    
    private MultiVirusCard multiVirusCard = new MultiVirusCard();

    @Override
    public MultiVirusCard getMultiOrganCardType(){
        return multiVirusCard;
    }
}

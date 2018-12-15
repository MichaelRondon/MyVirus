package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.SingleOrganCardType;

/**
 *
 */
public abstract class MedicineCard implements SingleOrganCardType<MultiMedicineCard>{
    
    private MultiMedicineCard multiMedicineCard = new MultiMedicineCard();

    @Override
    public MultiMedicineCard getMultiOrganCardType(){
        return multiMedicineCard;
    }

    @Override
    public String toString() {
        return String.format("Medicine: %s", this.getOrgan());
    }
}

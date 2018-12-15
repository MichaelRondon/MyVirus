package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Organ;

/**
 *
 */
public class CardFactory {
    
    private CardFactory() {
    }
    
    public static CardFactory getInstance() {
        return CardFactoryHolder.INSTANCE;
    }
    
    private static class CardFactoryHolder {

        private static final CardFactory INSTANCE = new CardFactory();
    }
    
    public Card getOrganCard(Organ organ){
        return new OrganCard() {
            @Override
            public Organ getOrgan() {
                return organ;
            }
        };
    }
    
    public Card getVirusCard(Organ organ){
        return new VirusCard() {
            @Override
            public Organ getOrgan() {
                return organ;
            }
        };
    }
    
    public Card getMedicineCard(Organ organ){
        return new MedicineCard() {
            @Override
            public Organ getOrgan() {
                return organ;
            }
        };
    }
}

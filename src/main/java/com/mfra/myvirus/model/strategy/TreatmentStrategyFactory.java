package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.cards.Card;
import com.mfra.myvirus.model.cards.Contagion;
import com.mfra.myvirus.model.cards.LatexGloves;
import com.mfra.myvirus.model.cards.MedicalError;
import com.mfra.myvirus.model.cards.OrganThief;
import com.mfra.myvirus.model.cards.Transplant;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class TreatmentStrategyFactory {
    
    private final Map<Class<?>, StrategyManager> treatmentStrategyMap = new HashMap<>();
    
    private TreatmentStrategyFactory() {
        treatmentStrategyMap.put(Transplant.class, new TrasplantStrategyManager());
        treatmentStrategyMap.put(OrganThief.class, new OrganThiefStrategyManager());
        treatmentStrategyMap.put(Contagion.class, new ContagionStrategyManager());
        treatmentStrategyMap.put(LatexGloves.class, new LatexGlovesStrategyManager());
        treatmentStrategyMap.put(MedicalError.class, new MedicalErrorStrategyManager());
    }
    
    public static TreatmentStrategyFactory getInstance() {
        return TreatmentStrategyFactoryHolder.INSTANCE;
    }
    
    private static class TreatmentStrategyFactoryHolder {

        private static final TreatmentStrategyFactory INSTANCE = new TreatmentStrategyFactory();
        
    }
    
    public StrategyManager getStrategyManager(Card card){
        if(treatmentStrategyMap.containsKey(card.getClass())){
            return treatmentStrategyMap.get(card.getClass());
        }
        throw new IllegalStateException("There's not implementation for the Treatment yet");
    }
}

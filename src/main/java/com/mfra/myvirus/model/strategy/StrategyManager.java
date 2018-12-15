package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.cards.Card;
import java.util.TreeSet;

/**
 *
 * @param <C>
 */     
public interface StrategyManager <C extends Card>{
    
    C  evaluate(C card, Rank currentRank, TreeSet<Rank> rivalsRanks);

}

package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.cards.Card;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @param <C>
 */     
public interface StrategyManager <C extends Card>{
    
    Optional<C>  evaluate(C card, Rank currentRank, Stream<Rank> rivalsRanks);

}

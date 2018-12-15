package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.cards.MedicalError;
import com.mfra.myvirus.model.cards.RankEvaluatorResult;
import com.mfra.myvirus.util.RankEvaluator;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class MedicalErrorStrategyManager implements StrategyManager<MedicalError> {

    @Override
    public MedicalError evaluate(MedicalError card, Rank currentRank,
                    TreeSet<Rank> rivalsRanks) {
        TreeSet<Rank> rivals = new TreeSet(rivalsRanks.stream().collect(Collectors.toList()));
        Optional<RankEvaluatorResult> evalRanks = RankEvaluator.getInstance()
                        .evalRanks(currentRank, rivals);
        if(evalRanks.isPresent()){
            RankEvaluatorResult rankEvaluatorResult = evalRanks.get();
            card.playCard(rankEvaluatorResult);
            return card;
        }
        
        return null;
    }
}

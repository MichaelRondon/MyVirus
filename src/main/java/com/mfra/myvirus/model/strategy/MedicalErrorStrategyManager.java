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
    public Optional<MedicalError> evaluate(MedicalError card, Rank currentRank,
                    Stream<Rank> rivalsRanks) {
        TreeSet<Rank> rivals = new TreeSet(rivalsRanks.collect(Collectors.toList()));
        Optional<RankEvaluatorResult> evalRanks = RankEvaluator.getInstance()
                        .evalRanks(currentRank, rivals);
        if(evalRanks.isPresent()){
            RankEvaluatorResult rankEvaluatorResult = evalRanks.get();
            card.playCard(rankEvaluatorResult);
            return Optional.of(card);
        }
        
        return Optional.empty();
    }
}

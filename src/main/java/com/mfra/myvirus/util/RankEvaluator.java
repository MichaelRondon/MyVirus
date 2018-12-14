package com.mfra.myvirus.util;

import com.mfra.myvirus.model.cards.RankEvaluatorResult;
import com.mfra.myvirus.model.strategy.Rank;
import java.util.Optional;
import java.util.TreeSet;

/**
 *
 */
public class RankEvaluator {
    
    private RankEvaluator() {
    }
    
    public static RankEvaluator getInstance() {
        return RankEvaluatorHolder.INSTANCE;
    }
    
    private static class RankEvaluatorHolder {

        private static final RankEvaluator INSTANCE = new RankEvaluator();
    }

    public Optional<RankEvaluatorResult> evalRanks(TreeSet<Rank> player1Ranks,
                    TreeSet<Rank> player2Ranks) {
        for (Rank player1Rank : player1Ranks) {
            Optional<RankEvaluatorResult> evalRanks = evalRanks(player1Rank,
                            player2Ranks);
            if (evalRanks.isPresent()) {
                return evalRanks;
            }
        }
        return Optional.empty();
    }

    public Optional<RankEvaluatorResult> evalRanks(Rank player1Rank,
                    TreeSet<Rank> player2Ranks) {
        for (Rank player2Rank : player2Ranks.descendingSet()) {
            if (player2Rank.getScore() > player1Rank.getScore()) {
                return Optional.of(new RankEvaluatorResult(player1Rank.getPlayer(),
                                player2Rank.getPlayer()));
            }
        }
        return Optional.empty();
    }
}

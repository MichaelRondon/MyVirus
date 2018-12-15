package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.cards.LatexGloves;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class LatexGlovesStrategyManager implements StrategyManager<LatexGloves> {

    @Override
    public LatexGloves evaluate(LatexGloves card, Rank currentRank,
                    TreeSet<Rank> rivalsRanks) {

        card.playCard(rivalsRanks.stream().map(rank -> {
            return rank.getPlayer();
        }).collect(Collectors.toList()));
        return card;
    }
}

package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.cards.LatexGloves;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class LatexGlovesStrategyManager implements StrategyManager<LatexGloves> {

    @Override
    public Optional<LatexGloves> evaluate(LatexGloves card, Rank currentRank,
                    Stream<Rank> rivalsRanks) {

        card.playCard(rivalsRanks.map(rank -> {
            return rank.getPlayer();
        }).collect(Collectors.toList()));
        return Optional.of(card);
    }
}

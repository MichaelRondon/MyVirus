package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.State;
import com.mfra.myvirus.model.cards.Contagion;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class ContagionStrategyManager implements StrategyManager<Contagion> {

    @Override
    public Optional<Contagion> evaluate(Contagion card, Rank currentRank,
                    Stream<Rank> rivalsRanks) {
        List<Organ> infectedOrgans = currentRank.getOrgansByStates(State.SICK);

        boolean playable;
        for (Organ organ : infectedOrgans) {
            playable = rivalsRanks.filter(rank -> {
                return rank.getOrgansByStates(State.HEALTH).contains(organ);
            }).findAny().isPresent();
            if (playable) {
                card.playCard(currentRank.getPlayer(),
                                rivalsRanks.map(Rank::getPlayer)
                                                .collect(Collectors.toList()));
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }
}

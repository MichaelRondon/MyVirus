package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.State;
import com.mfra.myvirus.model.cards.Contagion;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 */
public class ContagionStrategyManager implements StrategyManager<Contagion> {

    @Override
    public Contagion evaluate(Contagion card, Rank currentRank,
                    TreeSet<Rank> rivalsRanks) {
        List<Organ> infectedOrgans = currentRank.getOrgansByStates(State.SICK);

        boolean playable;
        for (Organ organ : infectedOrgans) {
            playable = rivalsRanks.stream().filter(rank -> {
                return rank.getOrgansByStates(State.HEALTH).contains(organ);
            }).findAny().isPresent();
            if (playable) {
                card.playCard(currentRank.getPlayer(),
                                rivalsRanks.stream().map(Rank::getPlayer)
                                                .collect(Collectors.toList()));
                return card;
            }
        }
        return null;
    }
}

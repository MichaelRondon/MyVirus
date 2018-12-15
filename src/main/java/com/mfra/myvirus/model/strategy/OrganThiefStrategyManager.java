package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.State;
import com.mfra.myvirus.model.cards.OrganThief;
import com.mfra.myvirus.model.cards.RankEvaluatorResult;
import com.mfra.myvirus.model.cards.TransplantEntry;
import com.mfra.myvirus.util.RankEvaluator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class OrganThiefStrategyManager implements StrategyManager<OrganThief> {

    private TrasplantStrategyManager trasplantStrategyManager
                    = new TrasplantStrategyManager();

    @Override
    public OrganThief evaluate(OrganThief card, Rank currentRank,
                    TreeSet<Rank> rivalsRanks) {

        TreeSet<Rank> healthAndImmuneEnemies = new TreeSet(rivalsRanks.stream().filter(rank -> {
            return rank.getOrgansByStates(State.HEALTH, State.VACCINATED, State.IMMUNE)
                            .isEmpty();
        }).collect(Collectors.toList()));

        List<Organ> infectedOrgans = currentRank.getOrgansByStates(State.SICK);
        if (!infectedOrgans.isEmpty()) {
            Optional<RankEvaluatorResult> targetRanks = RankEvaluator.getInstance()
                            .evalRanks(currentRank, healthAndImmuneEnemies);
            if (targetRanks.isPresent()) {
                return fillAndPlay(card, targetRanks.get());
            }
        }
        return null;
    }

    private OrganThief fillAndPlay(OrganThief organThief, RankEvaluatorResult evalRankResult) {
        TransplantEntry transplantEntry = new TransplantEntry(evalRankResult);
        trasplantStrategyManager.fillOrgans(transplantEntry);
        organThief.playCard(transplantEntry);
        return organThief;
    }
}

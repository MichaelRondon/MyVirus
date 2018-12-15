package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.State;
import com.mfra.myvirus.model.cards.Transplant;
import com.mfra.myvirus.model.cards.RankEvaluatorResult;
import com.mfra.myvirus.model.cards.TransplantEntry;
import com.mfra.myvirus.util.MyVirusUtil;
import com.mfra.myvirus.util.RankEvaluator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 */
public class TrasplantStrategyManager implements StrategyManager<Transplant> {

    @Override
    public Transplant evaluate(Transplant card, Rank currentRank,
                    TreeSet<Rank> rivalsRanks) {

        TreeSet<Rank> player2Ranks = new TreeSet(rivalsRanks.stream().filter(rank -> {
            return rank.getOrgansByStates(State.HEALTH, State.IMMUNE, State.VACCINATED).isEmpty();
        }).collect(Collectors.toList()));

        List<Organ> infectedOrgans = currentRank.getOrgansByStates(State.SICK);
        if (!infectedOrgans.isEmpty()) {
            Optional<RankEvaluatorResult> evalRanks = RankEvaluator.getInstance()
                            .evalRanks(currentRank, player2Ranks);
            if (evalRanks.isPresent()) {
                return fillAndPlay(card, evalRanks.get());
            }
        }

        TreeSet<Rank> withInfectedOrgans = new TreeSet(rivalsRanks.stream().filter(rank -> {
            return !rank.getOrgansByStates(State.SICK).isEmpty();
        }).collect(Collectors.toList()));
        Optional<RankEvaluatorResult> evalRanks = RankEvaluator.getInstance()
                        .evalRanks(withInfectedOrgans, player2Ranks);
        if (evalRanks.isPresent()) {
            return fillAndPlay(card, evalRanks.get());
        }
        return null;
    }

    private Transplant fillAndPlay(Transplant transplant, RankEvaluatorResult evalRankResult) {
        TransplantEntry transplantEntry = new TransplantEntry(evalRankResult);
        fillOrgans(transplantEntry);
        transplant.playCard(transplantEntry);
        return transplant;
    }

    void fillOrgans(TransplantEntry transplantEntry) {
        transplantEntry.setLessHealthyOrgan(MyVirusUtil.getInstance()
                        .getFirstOrgansByState(transplantEntry.getLessHealthyPlayer(), State.SICK)
                        .get());
        transplantEntry.setMostHealthyOrgan(MyVirusUtil.getInstance()
                        .getFirstOrgansByState(transplantEntry.getMostHealthyPlayer(), State.HEALTH,
                                        State.IMMUNE, State.VACCINATED)
                        .get());
    }
}

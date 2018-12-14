package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Hand;
import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.cards.Card;
import com.mfra.myvirus.util.MyVirusUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mfra.myvirus.model.SingleOrganCardType;
import com.mfra.myvirus.model.OrganCardType;
import java.util.ArrayList;

/**
 *
 */
public class CommonStrategy implements Strategy {

    @Override
    public List<Card> play(Player currentPlayer, List<Player> rivals) {
        Hand.UnmodifiableHandCard handCards = currentPlayer.getHandCards();
        Rank currentRank = new Rank(currentPlayer);
        Stream<Rank> rivalsRanks = rivals.stream().map(Rank::new);
        TreeSet<StrategyResp> virusToUse = new TreeSet(MyVirusUtil.getInstance()
                        .virusToUse(rivalsRanks, handCards.getVirus())
                        .collect(Collectors.toList()));
        Optional<Card> resp = testForTreatments(currentRank, rivalsRanks, handCards);
        if (resp.isPresent()) {
            return Collections.singletonList(resp.get());
        }

        resp = testForVirusIfWorth(virusToUse, currentRank);
        if (resp.isPresent()) {
            return Collections.singletonList(resp.get());
        }

        resp = testForOrgan(currentRank, handCards);
        if (resp.isPresent()) {
            return Collections.singletonList(resp.get());
        }

        resp = testMedForInfected(currentRank, handCards);
        if (resp.isPresent()) {
            return Collections.singletonList(resp.get());
        }

        resp = runOrganTypeCard(virusToUse);
        if (resp.isPresent()) {
            return Collections.singletonList(resp.get());
        }

        resp = testMedForHealthy(currentRank, handCards);
        if (resp.isPresent()) {
            return Collections.singletonList(resp.get());
        }

        if (!handCards.getOrgans().isEmpty()) {
            return new ArrayList(handCards.getOrgans());
        }

        if (!handCards.getMedicines().isEmpty()) {
            return new ArrayList(handCards.getMedicines());
        }

        if (!handCards.getVirus().isEmpty()) {
            return new ArrayList(handCards.getVirus());
        }

        throw new IllegalStateException("There aren't cards to play or discard");
    }

    private Optional<Card> testForTreatments(Rank currentRank,
                    Stream<Rank> rivalsRanks,
                    Hand.UnmodifiableHandCard handCards) {
        Collection<Card> treatments = handCards.getTreatments();
        for (Card treatment : treatments) {
            StrategyManager strategyManager = TreatmentStrategyFactory.getInstance().getStrategyManager(treatment);
            Optional<Card> evaluate = strategyManager.evaluate(treatment, currentRank, rivalsRanks);
            if (evaluate.isPresent()) {
                return evaluate;
            }

        }
        return Optional.empty();
    }

    private Optional<Card> testMedForInfected(Rank currentRank, Hand.UnmodifiableHandCard handCards) {
        TreeSet<StrategyResp> canUseMedForInfected = new TreeSet(MyVirusUtil.getInstance()
                        .canUseMedForInfected(currentRank, handCards.getMedicines())
                        .collect(Collectors.toList()));
        if (!canUseMedForInfected.isEmpty()) {
            return runOrganTypeCard(canUseMedForInfected);
        }
        return Optional.empty();
    }

    private Optional<Card> testMedForHealthy(Rank currentRank, Hand.UnmodifiableHandCard handCards) {
        TreeSet<StrategyResp> canUseMedForHealthy = new TreeSet(MyVirusUtil.getInstance()
                        .canUseMedForHealthy(currentRank, handCards.getMedicines())
                        .collect(Collectors.toList()));
        if (!canUseMedForHealthy.isEmpty()) {
            return runOrganTypeCard(canUseMedForHealthy);
        }
        return Optional.empty();
    }

    private Optional<Card> testForOrgan(Rank currentRank, Hand.UnmodifiableHandCard handCards) {
        TreeSet<StrategyResp> canUseOrgan = new TreeSet(MyVirusUtil.getInstance()
                        .canUseOrgan(currentRank, handCards.getOrgans())
                        .collect(Collectors.toList()));
        if (!canUseOrgan.isEmpty()) {
            return runOrganTypeCard(canUseOrgan);
        }
        return Optional.empty();
    }

    private Optional<Card> testForVirusIfWorth(TreeSet<StrategyResp> canUseVirus,
                    Rank currentRank) {
        if (!canUseVirus.isEmpty()) {
            StrategyResp singleStrategyResp = canUseVirus.last();
            Rank target = singleStrategyResp.getTarget();
            if (target.getScore() > currentRank.getScore()) {
                return runOrganTypeCard(singleStrategyResp, target);
            }
        }
        return Optional.empty();
    }

    private Optional<Card> runOrganTypeCard(TreeSet<StrategyResp> strategyResps) {
        if (!strategyResps.isEmpty()) {
            StrategyResp singleStrategyResp = strategyResps.last();
            return runOrganTypeCard(singleStrategyResp, singleStrategyResp.getTarget());
        }
        return Optional.empty();
    }

    private Optional<Card> runOrganTypeCard(StrategyResp singleStrategyResp, Rank target) {
        Card card = singleStrategyResp.getCard();
        if (card instanceof SingleOrganCardType) {
            ((SingleOrganCardType) card).playCard((Player) target.getPlayer());
            return Optional.of(card);
        }
        ((OrganCardType) card).playCard((Player) target.getPlayer(),
                        singleStrategyResp.getTargetOrgan());
        return Optional.of(card);

    }

}

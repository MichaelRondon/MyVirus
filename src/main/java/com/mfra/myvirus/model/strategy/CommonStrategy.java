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
import com.mfra.myvirus.model.SingleOrganCardType;
import com.mfra.myvirus.model.OrganCardType;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CommonStrategy implements Strategy {

    @Override
    public List<Card> play(Player currentPlayer, List<Player> rivals) {

        Hand.UnmodifiableHandCard handCards = currentPlayer.getHandCards();
        List<Supplier<Card>> checkers = fillChecker(handCards, currentPlayer, rivals);

        Optional<Card> findFirst = checkers.stream()
                        .map(Supplier::get)
                        .filter(Objects::nonNull)
                        .findFirst();
        if (findFirst.isPresent()) {
            return Collections.singletonList(findFirst.get());
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

    private List<Supplier<Card>> fillChecker(Hand.UnmodifiableHandCard handCards,
                    Player currentPlayer, List<Player> rivals) {
        Rank currentRank = new Rank(currentPlayer);
        TreeSet<Rank> rivalsRanks = rivals.stream().map(Rank::new).collect(Collectors.toCollection(TreeSet::new));
        TreeSet<StrategyResp> virusToUse = MyVirusUtil.getInstance()
                        .checkIfVirusCanBeUsed(rivalsRanks, handCards.getVirus())
                        .collect(Collectors.toCollection(TreeSet::new));

        List<Supplier<Card>> checkers = new ArrayList<>();
        checkers.add((Supplier<Card>) () -> {
            return testForTreatments(currentRank, rivalsRanks, handCards);
        });
        checkers.add((Supplier<Card>) () -> {
            return testForVirusIfWorth(virusToUse, currentRank);
        });
        checkers.add((Supplier<Card>) () -> {
            return testMedForInfected(currentRank, handCards);
        });
        checkers.add((Supplier<Card>) () -> {
            return testForOrgan(currentRank, handCards);
        });
        checkers.add((Supplier<Card>) () -> {
            return runOrganTypeCard(virusToUse);
        });
        checkers.add((Supplier<Card>) () -> {
            return testMedForHealthy(currentRank, handCards);
        });
        return checkers;
    }

    private Card testForTreatments(Rank currentRank,
                    TreeSet<Rank> rivalsRanks,
                    Hand.UnmodifiableHandCard handCards) {
        Collection<Card> treatments = handCards.getTreatments();
        for (Card treatment : treatments) {
            StrategyManager strategyManager = TreatmentStrategyFactory.getInstance().getStrategyManager(treatment);
            return strategyManager.evaluate(treatment, currentRank, rivalsRanks);
        }
        return null;
    }

    private Card testMedForInfected(Rank currentRank, Hand.UnmodifiableHandCard handCards) {
        TreeSet<StrategyResp> canUseMedForInfected = new TreeSet(MyVirusUtil.getInstance()
                        .canUseMedForInfected(currentRank, handCards.getMedicines())
                        .collect(Collectors.toList()));
        if (!canUseMedForInfected.isEmpty()) {
            return runOrganTypeCard(canUseMedForInfected);
        }
        return null;
    }

    private Card testMedForHealthy(Rank currentRank, Hand.UnmodifiableHandCard handCards) {
        TreeSet<StrategyResp> canUseMedForHealthy = new TreeSet(MyVirusUtil.getInstance()
                        .checkIfMedCanBeUsedForHealthy(currentRank, handCards.getMedicines())
                        .collect(Collectors.toList()));
        if (!canUseMedForHealthy.isEmpty()) {
            return runOrganTypeCard(canUseMedForHealthy);
        }
        return null;
    }

    private Card testForOrgan(Rank currentRank, Hand.UnmodifiableHandCard handCards) {
        TreeSet<StrategyResp> canUseOrgan = new TreeSet(MyVirusUtil.getInstance()
                        .canUseOrgan(currentRank, handCards.getOrgans())
                        .collect(Collectors.toList()));
        if (!canUseOrgan.isEmpty()) {
            return runOrganTypeCard(canUseOrgan);
        }
        return null;
    }

    private Card testForVirusIfWorth(TreeSet<StrategyResp> virusToUse,
                    Rank currentRank) {
        if (!virusToUse.isEmpty()) {
            StrategyResp singleStrategyResp = virusToUse.last();
            Rank target = singleStrategyResp.getTarget();
            if (target.getScore() > currentRank.getScore()) {
                return runOrganTypeCard(singleStrategyResp, target);
            }
        }
        return null;
    }

    private Card runOrganTypeCard(TreeSet<StrategyResp> strategyResps) {
        if (!strategyResps.isEmpty()) {
            StrategyResp singleStrategyResp = strategyResps.last();
            return runOrganTypeCard(singleStrategyResp, singleStrategyResp.getTarget());
        }
        return null;
    }

    private Card runOrganTypeCard(StrategyResp singleStrategyResp, Rank target) {
        Card card = singleStrategyResp.getCard();
        if (card instanceof SingleOrganCardType) {
            ((SingleOrganCardType) card).playCard((Player) target.getPlayer());
            return card;
        }
        ((OrganCardType) card).playCard((Player) target.getPlayer(),
                        singleStrategyResp.getTargetOrgan());
        return card;

    }

}

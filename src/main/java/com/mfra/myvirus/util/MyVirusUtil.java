package com.mfra.myvirus.util;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.State;
import com.mfra.myvirus.model.cards.Card;
import com.mfra.myvirus.model.cards.MultiOrganCard;
import com.mfra.myvirus.model.strategy.Rank;
import com.mfra.myvirus.model.strategy.StrategyResp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mfra.myvirus.model.SingleOrganCardType;

/**
 *
 */
public class MyVirusUtil {

    private static final Random RANDOM = new Random();

    private MyVirusUtil() {
    }

    public static MyVirusUtil getInstance() {
        return MyVirusUtilHolder.INSTANCE;
    }

    private static class MyVirusUtilHolder {

        private static final MyVirusUtil INSTANCE = new MyVirusUtil();
    }

    public List<Organ> getOrgansByState(Player iPlayer, State... states) {
        return iPlayer.getBody().entrySet().stream()
                        .filter(entry -> {
                            return Arrays.stream(states).anyMatch(state
                                            -> {
                                return entry.getValue().equals(state);
                            });
                        })
                        .map(entry -> {
                            return entry.getKey();
                        })
                        .collect(Collectors.toList());
    }

    public Optional<Organ> getFirstOrgansByState(Player iPlayer, State... state) {
        List<Organ> organsByState = getOrgansByState(iPlayer, state);

        return organsByState == null || organsByState.isEmpty() ? Optional.empty()
                        : Optional.of(organsByState.get(0));
    }

    public Stream<StrategyResp> canUseOrgan(Rank currentRank, Collection<Card> cards) {
        return cards.stream().map(organ -> {
            return canUseOrganCard(currentRank, organ);
        });
    }

    public Stream<StrategyResp> canUseMedForInfected(Rank currentRank, Collection<Card> cards) {
        return cards.stream().map(card -> {
            return canUseCardForInfected(currentRank, card);
        });
    }

    public Stream<StrategyResp> canUseMedForHealthy(Rank currentRank, Collection<Card> cards) {
        return cards.stream().map(card -> {
            return canUseCardForHealthy(currentRank, card);
        });
    }

    public Stream<StrategyResp> virusToUse(Stream<Rank> rivalsRanks, Collection<Card> viruses) {
        return viruses.stream().flatMap(virus -> {
            return virusToUse(rivalsRanks, virus);
        });
    }

    private Organ getRandomOrganWithout(List<Organ> allOrgans) {
        List<Organ> asList = Arrays.asList(Organ.values());
        asList.removeAll(allOrgans);
        return asList.get(RANDOM.nextInt(asList.size()));
    }

    private Stream<StrategyResp> virusToUse(Stream<Rank> rivalsRanks, Card virus) {
        return rivalsRanks.map(rank -> {
            return this.canUseCardForHealthy(rank, virus);
        }).filter(Objects::nonNull);
    }

    private StrategyResp canUseCardForHealthy(Rank targetRank, Card virusOrMedicine) {
        List<Organ> healthyOrgans = targetRank.getOrgansByStates(State.HEALTH, State.VACCINATED);
        return canUseCardInOrganList(targetRank, virusOrMedicine, healthyOrgans);
    }

    private StrategyResp canUseCardForInfected(Rank targetRank, Card virusOrMedicine) {
        List<Organ> infectedOrgans = targetRank.getOrgansByStates(State.SICK);
        return canUseCardInOrganList(targetRank, virusOrMedicine, infectedOrgans);
    }

    private StrategyResp canUseCardInOrganList(Rank targetRank, Card card,
                    List<Organ> list) {
        if (card instanceof SingleOrganCardType) {
            Organ organ = ((SingleOrganCardType) card).getOrgan();
            if (list.contains(organ)) {
                StrategyResp strategyResp = new StrategyResp(card, targetRank);
                strategyResp.setTargetOrgan(organ);
                return strategyResp;
            }
        }
        if (card instanceof MultiOrganCard) {
            if (!list.isEmpty()) {
                StrategyResp strategyResp = new StrategyResp(card, targetRank);
                strategyResp.setTargetOrgan(list.get(0));
                return strategyResp;
            }
        }
        return null;
    }

    private StrategyResp canUseOrganCard(Rank currentRank, Card card) {
        List<Organ> allOrgans = currentRank.getOrgansByStates(State.values());
        if (card instanceof SingleOrganCardType) {
            Organ organ = ((SingleOrganCardType) card).getOrgan();
            if (!allOrgans.contains(organ)) {
                StrategyResp strategyResp = new StrategyResp(card, currentRank);
                strategyResp.setTargetOrgan(organ);
                return strategyResp;
            }
        }
        if (card instanceof MultiOrganCard) {
            if (allOrgans.size() < 4) {
                StrategyResp strategyResp = new StrategyResp(card, currentRank);
                strategyResp.setTargetOrgan(getRandomOrganWithout(allOrgans));
                return strategyResp;
            }
        }
        return null;
    }
}

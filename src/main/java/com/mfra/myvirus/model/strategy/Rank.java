package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 */
public class Rank implements Comparable<Rank> {

    private final Map<State, List<Organ>> stateListOrgans;
    private final int score;
    private final Player player;

    public Rank(Player player) {
        this.player = player;
        stateListOrgans = player.getBody().entrySet().stream().collect(Collectors.groupingBy(
                        entry -> {
                            return entry.getValue();
                        },
                        Collectors.mapping(Entry::getKey, Collectors.toList())
        ));

        score = getOrgansByStates(State.IMMUNE).size() * 2
                        + getOrgansByStates(State.HEALTH).size()
                        + getOrgansByStates(State.VACCINATED).size();
    }

    public int getScore() {
        return score;
    }

    public final List<Organ> getOrgansByStates(State... states) {
        List resp = new ArrayList();
        Arrays.stream(states)
                        .filter(state -> {
                            return stateListOrgans.containsKey(state);
                        })
                        .forEach(state -> {
                            resp.addAll(stateListOrgans.get(state));
                        });
        return resp;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public int compareTo(Rank t) {
        return new Integer(score).compareTo(t.getScore());
    }

}

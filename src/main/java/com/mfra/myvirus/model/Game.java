package com.mfra.myvirus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class Game {

    private final List<Player> players;

    public Game(Player... player) {
        this.players = new ArrayList(Arrays.asList(player));
    }

    public List<Player> getRivals(Player currentPlayer) {
        return players.stream().filter(player -> {
            return !player.equals(currentPlayer);
        }).collect(Collectors.toList());
    }
}

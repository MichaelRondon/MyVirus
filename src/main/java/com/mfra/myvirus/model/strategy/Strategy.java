package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.cards.Card;
import java.util.List;

/**
 *
 */
public interface Strategy {

    public List<Card> play(Player currentPlayer, List<Player> rivals);
}

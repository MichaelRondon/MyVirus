package com.mfra.myvirus.service;

import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.cards.Card;
import java.util.List;


/**
 *
 */
public interface CardPlayerService {

    void play(Card card, Player currentPlayer, List<Player> rivals);
}

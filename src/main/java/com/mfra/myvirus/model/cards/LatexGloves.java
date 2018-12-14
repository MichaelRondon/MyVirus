package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.model.Player;
import java.util.List;

/**
 *
 */
public class LatexGloves implements Card {

    public void playCard(List<Player> rivalBodies) {
        rivalBodies.forEach(Player::discardAll);
    }

}

package com.mfra.myvirus.model.cards;

import com.mfra.myvirus.util.MyVirusUtil;
import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.State;
import java.util.List;

/**
 *
 */
public class Contagion implements Card {

    public void playCard(Player currentPlayer, List<Player> rivalBodies) {
        MyVirusUtil.getInstance().getOrgansByState(currentPlayer, State.SICK).forEach(
                        organ -> {
                            spreadPlayers(rivalBodies, organ);
                        });
    }

    private void spreadPlayers(List<Player> rivalBodies, Organ organ) {
        rivalBodies.forEach(body -> {
            body.spreadOrgan(organ);
        });
    }

}

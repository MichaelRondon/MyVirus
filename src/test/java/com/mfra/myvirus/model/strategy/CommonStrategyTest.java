package com.mfra.myvirus.model.strategy;

import com.mfra.myvirus.Application;
import com.mfra.myvirus.model.Organ;
import com.mfra.myvirus.model.Player;
import com.mfra.myvirus.model.State;
import com.mfra.myvirus.model.cards.CardFactory;
import com.mfra.myvirus.model.cards.MultiOrganCard;
import com.mfra.myvirus.model.cards.MultiVirusCard;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CommonStrategyTest {

    @Autowired
    private CommonStrategy commonStrategy;

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private List<Player> players = new ArrayList<>();

    @Before
    public void setUp() {
        player1 = new Player("player1", commonStrategy, players);
        player2 = new Player("player2", commonStrategy, players);
        player3 = new Player("player3", commonStrategy, players);
        player4 = new Player("player4", commonStrategy, players);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        player1.addCard(CardFactory.getInstance().getOrganCard(Organ.BRAIN));
        player1.addCard(CardFactory.getInstance().getMedicineCard(Organ.BONE));
        player1.addCard(CardFactory.getInstance().getMedicineCard(Organ.HEART));
        
        player2.addCard(CardFactory.getInstance().getVirusCard(Organ.BRAIN));
        player2.addCard(CardFactory.getInstance().getVirusCard(Organ.BRAIN));
        player2.addCard(CardFactory.getInstance().getVirusCard(Organ.BRAIN));
        
        player3.addCard(CardFactory.getInstance().getVirusCard(Organ.HEART));
        player3.addCard(CardFactory.getInstance().getOrganCard(Organ.BONE));
        player3.addCard(CardFactory.getInstance().getMedicineCard(Organ.BONE));

    }

    @Test
    public void testForOrganAndVirus() {
        
        player1.play();
        
        Assert.assertEquals(2, player1.getHandCards().getTotalCards());
        Assert.assertEquals(2, player1.getHandCards().getMedicines().size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.HEALTH).size());
        player1.addCard(CardFactory.getInstance().getMedicineCard(Organ.BRAIN));
        
        player2.play();
        Assert.assertEquals(2, player2.getHandCards().getTotalCards());
        Assert.assertEquals(2, player2.getHandCards().getVirus().size());
        Assert.assertEquals(0, new Rank(player2).getOrgansByStates(State.values()).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.SICK).size());
        player2.addCard(CardFactory.getInstance().getVirusCard(Organ.BRAIN));
        
        player3.play();
        Assert.assertEquals(2, player3.getHandCards().getTotalCards());
        Assert.assertEquals(1, player3.getHandCards().getVirus().size());
        Assert.assertEquals(1, player3.getHandCards().getMedicines().size());
        Assert.assertEquals(1, new Rank(player3).getOrgansByStates(State.HEALTH).size());
        player3.addCard(CardFactory.getInstance().getMedicineCard(Organ.BONE));
        
        player1.play();
        Assert.assertEquals(2, player1.getHandCards().getTotalCards());
        Assert.assertEquals(2, player1.getHandCards().getMedicines().size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.HEALTH).size());
        player1.addCard(CardFactory.getInstance().getOrganCard(Organ.BONE));
        
        player2.play();
        Assert.assertEquals(2, player2.getHandCards().getTotalCards());
        Assert.assertEquals(2, player2.getHandCards().getVirus().size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.SICK).size());
        player2.addCard(CardFactory.getInstance().getVirusCard(Organ.BRAIN));
        
        player3.play();
        Assert.assertEquals(2, player3.getHandCards().getTotalCards());
        Assert.assertEquals(1, player3.getHandCards().getVirus().size());
        Assert.assertEquals(1, player3.getHandCards().getMedicines().size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.values()).size());
        Assert.assertEquals(1, new Rank(player3).getOrgansByStates(State.VACCINATED).size());
        player3.addCard(CardFactory.getInstance().getMedicineCard(Organ.BONE));
        
        player1.play();
        Assert.assertEquals(2, player1.getHandCards().getTotalCards());
        Assert.assertEquals(2, player1.getHandCards().getMedicines().size());
        Assert.assertEquals(2, new Rank(player1).getOrgansByStates(State.values()).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.HEALTH).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.SICK).size());
        player1.addCard(CardFactory.getInstance().getMedicineCard(Organ.HEART));
        
        player2.play();
        Assert.assertEquals(2, player2.getHandCards().getTotalCards());
        Assert.assertEquals(2, player2.getHandCards().getVirus().size());
        Assert.assertEquals(0, new Rank(player2).getOrgansByStates(State.values()).size());
        Assert.assertEquals(0, new Rank(player1).getOrgansByStates(State.SICK).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.HEALTH).size());
        Assert.assertEquals(1, new Rank(player3).getOrgansByStates(State.VACCINATED).size());
        player2.addCard(CardFactory.getInstance().getVirusCard(Organ.BONE));
        
        player3.play();
        Assert.assertEquals(2, player3.getHandCards().getTotalCards());
        Assert.assertEquals(1, player3.getHandCards().getVirus().size());
        Assert.assertEquals(1, player3.getHandCards().getMedicines().size());
        Assert.assertEquals(1, new Rank(player3).getOrgansByStates(State.IMMUNE).size());
        player3.addCard(new MultiVirusCard());
        
        player1.play();
        Assert.assertEquals(2, player1.getHandCards().getTotalCards());
        Assert.assertEquals(2, player1.getHandCards().getMedicines().size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.values()).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.VACCINATED).size());
        player1.addCard(new MultiOrganCard());
        
        player2.play();
        Assert.assertEquals(2, player2.getHandCards().getTotalCards());
        Assert.assertEquals(2, player2.getHandCards().getVirus().size());
        Assert.assertEquals(0, new Rank(player2).getOrgansByStates(State.values()).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.HEALTH).size());
        player2.addCard(CardFactory.getInstance().getVirusCard(Organ.BONE));
        
        player3.play();
        Assert.assertEquals(2, player3.getHandCards().getTotalCards());
        Assert.assertEquals(1, player3.getHandCards().getVirus().size());
        Assert.assertEquals(1, player3.getHandCards().getMedicines().size());
        Assert.assertEquals(1, new Rank(player3).getOrgansByStates(State.IMMUNE).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.values()).size());
        player3.addCard(new MultiVirusCard());
        
        player1.play();
        Assert.assertEquals(2, player1.getHandCards().getTotalCards());
        Assert.assertEquals(2, player1.getHandCards().getMedicines().size());
        Assert.assertEquals(2, new Rank(player1).getOrgansByStates(State.values()).size());
        Assert.assertEquals(0, new Rank(player1).getOrgansByStates(State.VACCINATED).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.SICK).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.HEALTH).size());
        player1.addCard(CardFactory.getInstance().getOrganCard(Organ.STOMACH));
        
        player2.play();
        Assert.assertEquals(2, player2.getHandCards().getTotalCards());
        Assert.assertEquals(2, player2.getHandCards().getVirus().size());
        Assert.assertEquals(0, new Rank(player2).getOrgansByStates(State.values()).size());
        Assert.assertEquals(1, new Rank(player1).getOrgansByStates(State.HEALTH).size());
        player2.addCard(new MultiVirusCard());
        
        System.out.println("player1: "+player1.getBodyClon());
        System.out.println("player1: "+player1.getHandCards());
        System.out.println("player2: "+player2.getBodyClon());
        System.out.println("player2: "+player2.getHandCards());
        System.out.println("player3: "+player3.getBodyClon());
        System.out.println("player3: "+player3.getHandCards());
        
    }
    
}

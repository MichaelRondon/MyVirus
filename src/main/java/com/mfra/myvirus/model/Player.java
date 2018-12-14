package com.mfra.myvirus.model;

import com.mfra.myvirus.model.Hand.UnmodifiableHandCard;
import com.mfra.myvirus.model.strategy.Strategy;
import com.mfra.myvirus.model.cards.Card;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Player{

    private final Hand hand = new Hand();
    private final Map<Organ, State> bodyMap = new HashMap<>();
    private final Strategy strategy;
    private final String name;

    
    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public void discard(List<Card> cards) {
        hand.discard(cards);
    }

    public void discardAll() {
        hand.discardAll();
    }

    public UnmodifiableHandCard getHandCards() {
        return hand.getHandCards();
    }

    public Map<Organ, State> getBody() {
        return Collections.unmodifiableMap(bodyMap);
    }
    
    public void setBody(Map<Organ, State> body){
        bodyMap.clear();
        bodyMap.putAll(body);
    }

    public Map<Organ, State> getBodyClon() {
        Map<Organ, State> bodyClon = new HashMap<>();
        bodyClon.putAll(bodyMap);
        return bodyClon;
    }
    
    public String getName() {
        return name;
    }

    public void play(List<Player> rivals) {
        discard(strategy.play(this, rivals));
    }

    public State getStateAndRemove(Organ organ) {
        State state = this.bodyMap.get(organ);
        if (state.equals(State.IMMUNE)) {
            throw new IllegalStateException("An immune organ can not be removed");
        }
        this.bodyMap.remove(organ);
        return state;
    }

    public void putOrgan(Organ organ, State state) {
        putOrgan(organ);
        this.bodyMap.put(organ, state);
    }

    public void putOrgan(Organ organ) {
        if (bodyMap.containsKey(organ)) {
            State state = bodyMap.get(organ);
            switch (state) {
                case HEALTH:
                case VACCINATED:
                case IMMUNE:
                case SICK:
                    throw new IllegalStateException("The organ is already set");
            }
        }
        bodyMap.put(organ, State.SICK);
    }

    public void spreadOrgan(Organ organ) {
        State state = bodyMap.get(organ);
        if (state.equals(State.HEALTH)) {
            bodyMap.put(organ, State.SICK);
        }
    }

    public void sickOrgan(Organ organ) {
        if (!bodyMap.containsKey(organ)) {
            throw new IllegalStateException("You can not sick a non-existent organ");
        }
        State state = bodyMap.get(organ);
        switch (state) {
            case HEALTH:
                bodyMap.put(organ, State.SICK);
                break;
            case VACCINATED:
                bodyMap.put(organ, State.HEALTH);
                break;
            case IMMUNE:
                throw new IllegalStateException("You can not sick an immune organ");
            case SICK:
                bodyMap.remove(organ);
        }
    }

    public void vacuneOrgan(Organ organ) {
        if (!bodyMap.containsKey(organ)) {
            throw new IllegalStateException("You can not vacune a non-existent organ");
        }
        State state = bodyMap.get(organ);
        switch (state) {
            case HEALTH:
                bodyMap.put(organ, State.VACCINATED);
                break;
            case VACCINATED:
                bodyMap.put(organ, State.IMMUNE);
                break;
            case IMMUNE:
                throw new IllegalStateException("You can not vacune an immune organ");
            case SICK:
                bodyMap.put(organ, State.HEALTH);
        }
    }

}

package com.mfra.myvirus.model;

import com.mfra.myvirus.model.cards.Card;
import com.mfra.myvirus.model.cards.MedicineCard;
import com.mfra.myvirus.model.cards.MultiMedicineCard;
import com.mfra.myvirus.model.cards.MultiOrganCard;
import com.mfra.myvirus.model.cards.MultiVirusCard;
import com.mfra.myvirus.model.cards.OrganCard;
import com.mfra.myvirus.model.cards.VirusCard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Hand {

    private List<Card> medicines = new ArrayList<>();
    private List<Card> virus = new ArrayList<>();
    private List<Card> organs = new ArrayList<>();
    private List<Card> treatments = new ArrayList<>();
    private List<Card> allCards = new ArrayList<>();

    public void addCard(Card card) {
        allCards.add(card);
        if (card instanceof MedicineCard || card instanceof MultiMedicineCard) {
            medicines.add(card);
            return;
        }
        if (card instanceof VirusCard || card instanceof MultiVirusCard) {
            virus.add(card);
            return;
        }
        if (card instanceof OrganCard || card instanceof MultiOrganCard) {
            organs.add(card);
            return;
        }
        treatments.add(card);
    }

    public UnmodifiableHandCard getHandCards() {
        return new UnmodifiableHandCard();
    }

    public void discard(List<Card> cards) {
        medicines.removeAll(cards);
        virus.removeAll(cards);
        organs.removeAll(cards);
        treatments.removeAll(cards);
        allCards.removeAll(cards);
    }

    public void discardAll() {
        medicines.clear();
        virus.clear();
        organs.clear();
        treatments.clear();
        allCards.clear();
    }

    public class UnmodifiableHandCard {

        private UnmodifiableHandCard() {

        }

        public Collection<Card> getMedicines() {
            return Collections.unmodifiableCollection(medicines);
        }

        public Collection<Card> getVirus() {
            return Collections.unmodifiableCollection(virus);
        }

        public Collection<Card> getOrgans() {
            return Collections.unmodifiableCollection(organs);
        }

        public Collection<Card> getTreatments() {
            return Collections.unmodifiableCollection(treatments);
        }
        
        public int getTotalCards(){
            return medicines.size() + virus.size() + organs.size() + treatments.size();
        }

        @Override
        public String toString() {
            return String.format("Hand %s", allCards);
        }
    }
}

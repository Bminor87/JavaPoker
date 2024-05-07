package poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    public List<Card> cards;

    public Deck() {
        this.reset();
        this.shuffle();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int randomIndex = random.nextInt(cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(randomIndex));
            cards.set(randomIndex, temp);
        }
    }

    public Card draw() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public void discard(Card card) {
        // Implement discard
    }

    public void add(Card card) {
        // Implement add
    }

    public void reset() {
        this.cards = new ArrayList<>();
        String[] suits = {"♠", "♣", "♦", "♥"};
        for (String suit : suits) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(suit, i));
            }
        }
    }


}

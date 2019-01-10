

import java.util.ArrayList;
import java.util.List;


public class Deck {

    private List<Card> undealtCards;
    private List<Card> dealtCards;

    
    public Deck (String[] ranks, String[] suits, int[] values) {
        undealtCards = new ArrayList<>();
        dealtCards = new ArrayList<>();
        for (String suit : suits) {


            int i = 0;
            for (String rank : ranks) {


                if (i >= values.length) {
                    i = 0;
                }
                Card card = new Card(rank, suit, values[i]);
                undealtCards.add(card);
                i++;
            }
        }
    }

    
    public boolean isEmpty() {
        if (undealtCards.size() == 0) {
            return true;
        }
        return false;
    }

    
    public int size() { return undealtCards.size(); }

    public Card deal() {
        if (undealtCards.size() != 0) {
            dealtCards.add(undealtCards.get(0));
            undealtCards.remove(0);
            return dealtCards.get(dealtCards.size() - 1);
        }
        return null;
    }

    
    public void shuffle() {
        undealtCards.addAll(dealtCards);
        dealtCards.clear();
        shuffleHelper(undealtCards);
    }

    
    private void shuffleHelper(List<Card> deck) {
        for (int i = deck.size() - 1; i >= 0; i--) {
            int randomPos = (int)(Math.random() * i);
            Card tempCard = deck.get(randomPos);
            deck.set(randomPos, deck.get(i));
            deck.set(i, tempCard);
        }
    }

    
    public String getUndealtCards() {
        String string = "Undealt Cards: " + "\n" + "\n";
        for (Card card : undealtCards) {
            string += card + "\n";
        }
        return string;
    }

    
    public String getDealtCards() {
        String string = "Dealt Cards: " + "\n" + "\n";
        for (Card card : dealtCards) {
            string += card + "\n";
        }
        return string;
    }
}
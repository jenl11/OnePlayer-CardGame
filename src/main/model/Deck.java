package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a list that consist of cards
 */
public class Deck {

    public static final int DEALING_AMOUNT = 7;
    private List<Card> deck;

    //EFFECT: creates an empty deck
    public Deck() {
        deck = new ArrayList<>();
    }

    //REQUIRES: numOfCards has to be 24 cards
    //EFFECT: creates a deck of cards with half the normal
    // amount of cards of a normal deck and without the kings
    public Deck(int numOfCards) {
        deck = new ArrayList<>(numOfCards);
        for (int i = 1; i < 13; i++) {
            deck.add(new Card("b", i));
            deck.add(new Card("r", i));
        }
    }

    public int getSize() {
        return deck.size();
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public Card getCard(int index) {
        return deck.get(index);
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    //EFFECT: returns true if the deck contains the given card
    //otherwise, returns false
    public boolean containsCard(Card c) {
        for (Card card : deck) {
            if (c.sameCard(card)) {
                return true;
            }
        }
        return false;
    }

    //Code reference: https://www.journaldev.com/32661/shuffle-array-java
    //MODIFIES: this
    //EFFECT: shuffles the deck
    public void shuffle() {
        Collections.shuffle(deck);
    }

    //MODIFIES: this
    //EFFECT: adds another deck to this deck
    public void addDeck(Deck otherDeck) {
        for (int i = 0; i < otherDeck.getSize(); i++) {
            deck.add(otherDeck.getCard(i));
        }
    }

    //MODIFIES: this
    //EFFECT: removes all the cards from this deck that are also in the otherDeck
    public void removeDeck(Deck otherDeck) {
        for (int i = 0; i < otherDeck.getSize(); i++) {
            Card otherCard = otherDeck.getCard(i);
            for (int j = 0; j < deck.size(); j++) {
                Card thisCard = deck.get(j);
                if (otherCard.sameCard(thisCard)) {
                    deck.remove(thisCard);
                }
            }
        }

    }

    //REQUIRES: card must be in the deck
    //MODIFIES: this
    //EFFECT: removes a specific card from the deck and returns that card
    public Card removeCard(Card card) {
        deck.remove(card);
        return card;
    }

    //MODIFIES: this, cards
    //EFFECT: removes 3 cards from the otherDeck and adds it to this deck
    // but if there is less than 3 cards in otherDeck, all cards are removed
    //from the otherDeck and added to this deck
    public void removeTopThree(Deck otherDeck) {
        if (otherDeck.getSize() >= 3) {
            for (int i = 0; i < 3; i++) {
                Card card = otherDeck.getCard(0);
                deck.add(otherDeck.removeCard(card));
            }
        } else {
            int size = otherDeck.getSize();
            for (int i = 0; i < size; i++) {
                Card card = otherDeck.getCard(0);
                deck.add(otherDeck.removeCard(card));
            }
        }
    }

    //REQUIRES: this deck needs to contain at least DEALING_AMOUNT cards
    //MODIFIES: This
    //EFFECT: removes the amount of cards needed to be dealt from
    // this deck and returns those cards as a deck
    public Deck dealHand() {
        Deck cards = new Deck();
        for (int i = 0; i < DEALING_AMOUNT; i++) {
            cards.addCard(deck.get(deck.size() - 1));
            deck.remove(deck.get(deck.size() - 1));
        }
        return cards;
    }

    //EFFECT: returns true if the colours of the cards in the deck are alternating
    //otherwise, returns false
    public boolean isAlternatingColour() {
        for (int i = 0; i < deck.size() - 1; i++) {
            String colour = deck.get(i).getColour();
            String colour2 = deck.get(i + 1).getColour();
            if (colour.equals(colour2)) {
                return false;
            }
        }
        return true;

    }

    //EFFECT: returns true if the ranks of the cards in the deck is
    // in consecutive and descending order, otherwise, returns false
    public boolean isDescendingRank() {
        for (int i = 0; i < deck.size() - 1; i++) {
            int rank = deck.get(i).getRank();
            int rank2 = deck.get(i + 1).getRank();
            if (rank != rank2 + 1) {
                return false;
            }
        }
        return true;
    }

    //Code reference for line 166: https://howtodoinjava.com/java-sorting-guide/
    //MODIFIES: This
    //EFFECT: sorts the deck in descending rank order
    public void sortDeck() {
        boolean isNotSorted = true;
        while (isNotSorted) {
            ArrayList<Integer> listOfRanks = new ArrayList<>();
            for (Card value : deck) {
                listOfRanks.add(value.getRank());
            }
            ArrayList<Integer> sortedRank = new ArrayList<>(listOfRanks);
            sortedRank.sort(Collections.reverseOrder());
            if (sortedRank.equals(listOfRanks)) {
                isNotSorted = false;
            }
            for (int i = 0; i < deck.size() - 1; i++) {
                int rank = deck.get(i).getRank();
                int rank2 = deck.get(i + 1).getRank();
                if (rank < rank2) {
                    Card card = deck.get(i);
                    Card card2 = deck.get(i + 1);
                    deck.set(i, card2);
                    deck.set(i + 1, card);
                }
            }
        }
    }

    //EFFECT: returns an array list of the deck
    public Object[] toArray() {
        return deck.toArray();
    }
}

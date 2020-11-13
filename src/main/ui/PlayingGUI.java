package ui;

import model.Card;
import model.Deck;

public class PlayingGUI {
    private static final int DECK_QUANTITY = 24;

    Deck deck;
    Deck hand;
    Deck red;
    Deck black;

    public PlayingGUI() {
        deck = new Deck(DECK_QUANTITY);
        deck.shuffle();
        hand = deck.dealHand();
        red = new Deck();
        black = new Deck();
        red.addCard(new Card("r", 13));
        red.addCard(new Card("r", 13));
    }

    public void display() {

    }



}

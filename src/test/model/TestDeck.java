package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;

class TestDeck {

    private final int dealingAmount = Deck.DEALING_AMOUNT;
    private Deck deck;
    private Deck deckWithCards;

    @BeforeEach
    public void setup() throws Exception {
        deck = new Deck();
        deckWithCards = new Deck(24);
    }

    @Test
    public void testEmptyConstructor() {
        assertEquals(0, deck.getSize());
        assertTrue(deck.isEmpty());
    }

    @Test
    public void testConstructorWithCards() {
        assertEquals(24, deckWithCards.getSize());
        assertFalse(deckWithCards.isEmpty());
    }

    @Test
    public void containsTest() {
        Card card1 = new Card("r", 6);
        Card card2 = new Card("r", 6);

        deck.addCard(card1);

        assertTrue(deck.containsCard(card2));
    }

    @Test
    public void notContainsTest() {
        Card card1 = new Card("r", 6);
        Card card2 = new Card("r", 8);

        deck.addCard(card1);

        assertFalse(deck.containsCard(card2));
    }

    @Test
    public void shuffleTest() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        Card card3 = new Card("r", 3);
        Card card4 = new Card("b", 4);
        Card card5 = new Card("r", 5);
        Card card6 = new Card("b", 6);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(card6);

        List<Integer> ranks = new ArrayList<>();
        for (int i = 0; i < deck.getSize(); i++) {
            ranks.add(deck.getCard(i).getRank());
        }
        deck.shuffle();

        List<Integer> ranksShuffled = new ArrayList<>();
        for (int j = 0; j < deck.getSize(); j++) {
            ranksShuffled.add(deck.getCard(j).getRank());
        }
        assertNotEquals(ranksShuffled, ranks);
    }

    @Test
    public void testRemoveAll() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 1);
        deck.addCard(card1);
        deck.addCard(card2);
        assertEquals(2, deck.getSize());
        deck.removeAll();
        assertEquals(0, deck.getSize());
    }

    @Test
    public void testEquals() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("r", 1);
        Deck deck1 = new Deck();
        deck.addCard(card1);
        deck1.addCard(card2);
        assertTrue(deck.equals(deck1));
    }

    @Test
    public void testNotEquals() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        Deck deck1 = new Deck();
        deck.addCard(card1);
        deck1.addCard(card2);
        assertFalse(deck.equals(deck1));
    }

    @Test
    public void testNotEqualsDifferentClass() {
        Card card1 = new Card("r", 1);
        assertFalse(deck.equals(card1));
    }

    @Test
    public void testNotEqualsNull() {
        Deck deck1 = null;
        assertFalse(deck.equals(deck1));
    }

    @Test
    public void testDifferentHashCode() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        Deck deck1 = new Deck();
        deck.addCard(card1);
        deck1.addCard(card2);
        assertNotEquals(deck1, deck);
        assertTrue(deck.hashCode() != deck1.hashCode());
    }

    @Test
    public void testSameHashCode() {
        Card card1 = new Card("r", 1);
        Deck deck1 = new Deck();
        deck.addCard(card1);
        deck1.addCard(card1);
        assertEquals(deck1, deck);
        assertEquals(deck1.hashCode(), deck.hashCode());
    }

    @Test
    public void testAddNothing() {
        Deck otherDeck = new Deck();
        deck.addDeck(otherDeck);

        assertEquals(0, deck.getSize());
    }

    @Test
    public void testAddDeck() {
        Deck otherDeck = new Deck();
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        Card card3 = new Card("r", 3);

        otherDeck.addCard(card1);
        otherDeck.addCard(card2);
        otherDeck.addCard(card3);

        deck.addDeck(otherDeck);

        assertEquals(3, deck.getSize());
    }

    @Test
    public void testRemoveNothing() {
        Deck otherDeck = new Deck();
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);

        otherDeck.addCard(card1);
        otherDeck.addCard(card2);

        Card card3 = new Card("r", 8);
        Card card4 = new Card("b", 11);
        Card card5 = new Card("b", 13);

        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);

        deck.removeDeck(otherDeck);

        assertEquals(3, deck.getSize());
    }

    @Test
    public void testRemoveDeck() {
        Deck otherDeck = new Deck();
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        Card card3 = new Card("r", 3);
        Card card4 = new Card("b", 4);

        otherDeck.addCard(card1);
        otherDeck.addCard(card2);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        deck.removeDeck(otherDeck);

        assertEquals(2, deck.getSize());
    }

    @Test
    public void testRemoveCard() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        deck.addCard(card1);
        deck.addCard(card2);
        assertEquals(2, deck.getSize());

        assertEquals(card1, deck.removeCard(card1));
        assertEquals(card2, deck.removeCard(card2));

        assertEquals(0, deck.getSize());
    }

    @Test
    public void testRemoveTopThree() {
        Deck otherDeck = new Deck();
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        Card card3 = new Card("r", 3);
        Card card4 = new Card("b", 4);
        otherDeck.addCard(card1);
        otherDeck.addCard(card2);
        otherDeck.addCard(card3);
        otherDeck.addCard(card4);

        deck.removeTopThree(otherDeck);

        assertEquals(3, deck.getSize());
        assertEquals(card1, deck.getCard(0));
        assertEquals(card2, deck.getCard(1));
        assertEquals(card3, deck.getCard(2));

        assertEquals(1, otherDeck.getSize());
    }


    @Test
    public void testRemoveTopNotThree() {
        Deck otherDeck = new Deck();
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        otherDeck.addCard(card1);
        otherDeck.addCard(card2);

        deck.removeTopThree(otherDeck);

        assertEquals(2, deck.getSize());
        assertEquals(card1, deck.getCard(0));
        assertEquals(card2, deck.getCard(1));

        assertEquals(0, otherDeck.getSize());
    }

    @Test
    public void testDealHand() {
        Deck hand = deckWithCards.dealHand();

        assertEquals(dealingAmount, hand.getSize());
        assertEquals(17, deckWithCards.getSize());
    }

    @Test
    public void testNotAlternatingColour() {
        Card card1 = new Card("r", 6);
        Card card2 = new Card("r", 5);
        Card card3 = new Card("b", 4);
        Card card4 = new Card("b", 3);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        assertFalse(deck.isAlternatingColour());
    }

    @Test
    public void testAlternatingColour() {
        Card card1 = new Card("r", 6);
        Card card2 = new Card("b", 5);
        Card card3 = new Card("r", 4);
        Card card4 = new Card("b", 3);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        assertTrue(deck.isAlternatingColour());
    }

    @Test
    public void isDescendingRankTest() {
        Card card1 = new Card("r", 4);
        Card card2 = new Card("b", 3);
        Card card3 = new Card("r", 2);
        Card card4 = new Card("b", 1);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        assertTrue(deck.isDescendingRank());
    }

    @Test
    public void sameRankNotConsecutive() {
        Card card1 = new Card("r", 6);
        Card card2 = new Card("b", 5);
        Card card3 = new Card("r", 4);
        Card card4 = new Card("b", 4);
        Card card5 = new Card("r", 3);
        Card card6 = new Card("r", 2);
        Card card7 = new Card("b", 1);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(card6);
        deck.addCard(card7);

        assertFalse(deck.isDescendingRank());
    }

    @Test
    public void notConsecutiveRank() {
        Card card1 = new Card("r", 6);
        Card card2 = new Card("b", 5);
        Card card3 = new Card("r", 10);
        Card card4 = new Card("b", 4);
        Card card5 = new Card("r", 7);
        Card card6 = new Card("b", 1);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(card6);

        assertFalse(deck.isDescendingRank());
    }

    @Test
    public void testSortDeck() {
        Card card1 = new Card("r", 6);
        Card card2 = new Card("b", 5);
        Card card3 = new Card("r", 8);
        Card card4 = new Card("r", 4);
        Card card5 = new Card("b", 7);
        Card card6 = new Card("b", 3);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(card6);
        assertFalse(deck.isDescendingRank());
        assertFalse(deck.isAlternatingColour());

        deck.sortDeck();
        assertTrue(deck.isDescendingRank());
        assertTrue(deck.isAlternatingColour());
    }

    @Test
    public void sortedEvenWhenNotConsecutive() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 5);
        Card card3 = new Card("r", 3);
        Card card4 = new Card("b", 9);
        Card card5 = new Card("r", 6);
        Card card6 = new Card("b", 8);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(card6);

        List<Integer> ranks = new ArrayList<>();
        for (int i = 0; i < deck.getSize(); i++) {
            ranks.add(deck.getCard(i).getRank());
        }
        ranks.sort(Collections.reverseOrder());
        deck.sortDeck();

        List<Integer> ranksSorted = new ArrayList<>();
        for (int j = 0; j < deck.getSize(); j++) {
            ranksSorted.add(deck.getCard(j).getRank());
        }
        assertEquals(ranksSorted, ranks);
    }

    @Test
    public void testPrint() {
        Card card1 = new Card("r", 2);
        Card card2 = new Card("b", 1);

        deck.addCard(card1);
        deck.addCard(card2);

        String print = "[<r,2>, <b,1>]";

        assertEquals(print,Arrays.toString(deck.toArray()));
    }


}
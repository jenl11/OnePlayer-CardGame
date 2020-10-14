package ui;

import model.Card;
import model.Deck;

import java.util.Arrays;
import java.util.Scanner;

//Code reference fo any scanner methods: TellerApp and https://www.javatpoint.com/Scanner-class
/**
 * Represents the game of King's Row
 */
public class Game {

    private static final int DECK_QUANTITY = 24;
    private Deck deck;
    private Deck hand;
    private Deck startWithRed;
    private Deck startWithBlack;
    private Scanner input;
    private int round;

    public Game() {
        deck = new Deck(DECK_QUANTITY);
        deck.shuffle();
        hand = new Deck();
        hand = deck.dealHand();
        startWithRed = new Deck();
        startWithBlack = new Deck();
        startWithRed.addCard(new Card("r",13));
        startWithBlack.addCard(new Card("b", 13));
        input = new Scanner(System.in);
        round = 0;
    }

    public void play() {
        boolean play = playOrQuit();
        while (play) {
            if (hand.isEmpty() && deck.isEmpty() || hand.isEmpty()) {
                System.out.println("YAY! You have finished the game in " + round + " rounds!\nGood work! :)");
                break;
            }
            layout();
            question();
            String answer = input.next();
            answer = answer.toLowerCase();
            if (answer.equals("yes")) {
                yesPlaying();
            } else if (answer.equals("q")) {
                break;
            } else if (!deck.isEmpty()) {
                System.out.println("3 cards were added to your hand because you couldn't play any cards.");
                hand.removeTopThree(deck);
            } else {
                gameOver();
                play = false;
            }
        }
    }


    public void gameOver() {
        System.out.println("There are no more cards in main deck to be given and you have no more cards");
        System.out.println("that can be discarded...\nGame Over :(");
    }

    public void question() {
        System.out.println("Do you have at least 2 cards with a alternating colour and ");
        System.out.println("consecutive rank that can go after the last card in either Deck 1 or 2?");
        System.out.println("Please answer 'yes' or 'no'. If you would like to quit, answer 'q'.");
    }


    public boolean playOrQuit() {
        System.out.println("Play --> p");
        System.out.println("Quit --> q");
        String play = input.next();
        play  = play.toLowerCase();
        if (play.equals("p")) {
            return true;
        }
        return false;
    }

    //Code reference for array print statements: https://stackoverflow.com/questions/9265719/print-arraylist
    public void layout() {
        round = round + 1;
        System.out.println("Round: " + round);
        System.out.println("Row 1: ");
        System.out.println(Arrays.toString(startWithRed.toArray()));
        System.out.println("Row 2: ");
        System.out.println(Arrays.toString(startWithBlack.toArray()));
        System.out.println("\nHere is your hand: ");
        hand.sortDeck();
        System.out.println(Arrays.toString(hand.toArray()));
    }


    public void yesPlaying() {
        Deck putInTo = addingToWhichDeck();
        Deck getRidOf;
        boolean notEligibleCard = true;
        while (notEligibleCard) {
            getRidOf = enteringCards();
            getRidOf.sortDeck();
            putInTo.addDeck(getRidOf);
            putInTo.sortDeck();
            if (putInTo.isAlternatingColour() && putInTo.isDescendingRank()) {
                hand.removeDeck(getRidOf);
                notEligibleCard = false;
            } else {
                System.out.println("Cards are not alternating colour or consecutive rank. Please try again.");
                putInTo.removeDeck(getRidOf);
                notEligibleCard = true;
            }
        }
    }


    public Deck addingToWhichDeck() {
        Deck putInTo = new Deck();
        boolean notCorrectChoice = true;
        while (notCorrectChoice) {
            System.out.println("For which Deck? 1 or 2?");
            String whichDeck = input.next();
            whichDeck = whichDeck.toLowerCase();
            if (whichDeck.equals("1")) {
                putInTo = startWithRed;
                notCorrectChoice = false;
            } else if (whichDeck.equals("2")) {
                putInTo = startWithBlack;
                notCorrectChoice = false;
            } else {
                System.out.println("That was neither 1 or 2. Try again.");
            }
        }
        return putInTo;
    }


    public Deck enteringCards() {
        Card card = userInputCard();
        Deck getRidOf = new Deck();
        getRidOf.addCard(card);
        boolean flag = true;
        while (flag) {
            System.out.println("Do you have more cards to add? Please answer yes or no.");
            String answer = input.next();
            answer = answer.toLowerCase();
            if (answer.equals("yes")) {
                getRidOf.addCard(userInputCard());
            } else if (getRidOf.getSize() < 2) {
                System.out.println("Not enough cards, please enter more.");
                getRidOf.addCard(userInputCard());
            } else {
                flag = false;
            }
        }
        return getRidOf;
    }



    private Card userInputCard() {
        String colour = inputColour();
        int rank = inputRank();
        Card newCard = new Card(colour, rank);
        boolean flag = true;
        while (flag) {
            if (hand.containsCard(newCard)) {
                flag = false;
            } else {
                System.out.println("The card entered is not a card in your hand. Try again.");
                colour = inputColour();
                rank = inputRank();
                newCard = new Card(colour,rank);
            }
        }
        return newCard;
    }

    private String inputColour() {
        System.out.println("Enter colour: ");
        String colour = input.next();
        colour = colour.toLowerCase();
        boolean redOrBlack = true;
        while (redOrBlack) {
            if (colour.equals("r") || colour.equals("b")) {
                redOrBlack = false;
            } else {
                System.out.println("That was neither 'r' or 'b'. Please enter again.");
                colour = input.next();
            }
        }
        return colour;
    }

    private int inputRank() {
        System.out.println("Enter rank: ");
        int rank = input.nextInt();
        boolean inRange = true;
        while (inRange) {
            if (rank > 0 && rank < 13) {
                inRange = false;
            } else {
                System.out.println("Rank out of range. Please enter rank between 1 and 12.");
                rank = input.nextInt();
            }
        }
        return rank;
    }

}

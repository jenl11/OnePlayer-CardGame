package ui;

import model.Card;
import model.Deck;
import model.GameDecks;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

//Code reference fo any scanner methods: TellerApp and https://www.javatpoint.com/Scanner-class
/**
 * Represents the game of King's Row
 * Displaying the game for the player and allowing the
 * player to input into the console to play the game.
 */
public class Game {
    private static final String JSON_STORE = "./data/game.json";
    private static final int DECK_QUANTITY = 24;
    private Deck deck;
    private Deck hand;
    private Deck startWithRed;
    private Deck startWithBlack;
    private Scanner input;
    private int round;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECT: Sets up the game with the needed items
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
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECT: in charge of running the game and determines
    // whether the player wants to continue playing, save the game, quit the game
    // or if the player has won or lost the game
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
            if (answer.toLowerCase().equals("yes")) {
                yesPlaying();
            } else if (answer.toLowerCase().equals("s")) {
                saveGame();
            } else if (answer.toLowerCase().equals("q")) {
                break;
            } else if (!deck.isEmpty()) {
                System.out.println("\n3 cards were added to your hand because you couldn't play any cards.");
                hand.removeTopThree(deck);
            } else {
                gameOver();
                play = false;
            }
        }
    }

    //EFFECT: helper method to print statements for
    // when the game is over and the player has lost
    private void gameOver() {
        System.out.println("There are no more cards in main deck to be given and you have no more cards");
        System.out.println("that can be discarded...\nGame Over :(");
    }

    //EFFECT: helper method to print statement for the
    // player asking if they have the certain cards to play
    private void question() {
        System.out.println("Do you have at least 2 cards with a alternating colour and ");
        System.out.println("consecutive rank that can go after the last card in either Deck 1 or 2?");
        System.out.println("Please answer 'yes' or 'no'");
        System.out.println("If you want to **save** the game, answer 's'");
        System.out.println("If you want to **quit** the game, answer 'q'");

    }

    //EFFECT: returns true if the player wants to play or they want to load the game from a previous round
    // Otherwise returns false
    private boolean playOrQuit() {
        System.out.println("Play -> p");
        System.out.println("load -> l");
        System.out.println("Quit -> q");
        String play = input.next();
        play  = play.toLowerCase();
        if (play.equals("l")) {
            loadGame();
        }
        return play.equals("p") || play.equals("l");
    }

    //Code reference for array print statements: https://stackoverflow.com/questions/9265719/print-arraylist
    //EFFECT: helper method that prints out the layout of the game for the player
    // to see what round of the game it is, the two decks
    // with the kings and their hand of cards
    private void layout() {
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


    //EFFECT: helper method for when the player says yes to having cards to play
    // and tells the user if the cards they entered meet the requirements of the game,
    // if it does not, then it allows the player to try again
    private void yesPlaying() {
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

    //EFFECT: returns either startWithRed or startWithBlack
    // from what the player chooses to put their cards into.
    // If the player answers neither of the options given, it allows the
    // player to enter their answer again
    private Deck addingToWhichDeck() {
        Deck putInTo = new Deck();
        boolean notCorrectChoice = true;
        while (notCorrectChoice) {
            System.out.println("For which Row? 1 or 2?");
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

    //EFFECT: returns a deck containing the cards the user has chosen from
    // their hand. However, if the deck contains less than 2 cards, the user
    // is prompted to enter more cards to add to the deck until the deck has
    // 2 or more cards
    private Deck enteringCards() {
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


    //EFFECT: returns a cards that the user inputs
    // If that card is not in their hand, it allows the
    // player to enter a card again until they have entered
    // a card that is in their hand
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

    //EFFECT: returns either "r" or "b" to represent the colour of the card
    // the player inputs. If the user inputs anything else, then the game
    // allows the user to enter again until they have entered correctly "r" or "b"
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

    //EFFECT: returns an integer representing the rank of the card
    // the player inputs. If the player inputs a rank that is not in range
    // of 1 to 12, then it allows the player to enter again until they
    // input an int within the range
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

    //EFFECTS: saves the game to the game.json file in data
    private void saveGame() {
        try {
            jsonWriter.open();
            GameDecks decks = new GameDecks(deck, hand, startWithRed, startWithBlack);
            jsonWriter.write(decks);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: loads the game using the decks stored in game.json file
    private void loadGame() {
        try {
            GameDecks decks = jsonReader.read();
            deck = decks.getDeck(0);
            hand = decks.getDeck(1);
            startWithRed = decks.getDeck(2);
            startWithBlack = decks.getDeck(3);
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

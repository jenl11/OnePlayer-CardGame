package ui;

import model.Card;
import model.Deck;
import model.GameDecks;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Represents the game of King's Row as a GUI
 * Reference for the usage of SwingConstants: https://www.tutorialspoint.com/how-can-we-add-multiple-sub-panels-to-the-main-panel-in-java
 */
public class GameApp extends JFrame {
    private static final String JSON_STORE = "./data/game.json";
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 800;
    JPanel buttons;
    JPanel gameOptions;
    JPanel handPanel;
    JPanel redPanel;
    JPanel blackPanel;
    JPanel ridOfPanel;
    JLabel gameStatus;
    JLabel menuPage;
    JTextArea rules;
    Deck deck;
    Deck hand;
    Deck red;
    Deck black;
    Deck getRidOf;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECT: sets up the JFrame and all the decks in the game
    public GameApp() {
        super("King's Row");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        deck = new Deck(24);
        deck.shuffle();
        hand = deck.dealHand();
        red = new Deck();
        black = new Deck();
        getRidOf = new Deck();
        red.addCard(new Card("r", 13));
        black.addCard(new Card("b", 13));
        initialDisplay();
    }

    //MODIFIES: this
    //EFFECT: initializes the starting page of the game
    private void initialDisplay() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0,1));
        setSize(new Dimension(WIDTH,HEIGHT));
        titlePage();
        buttons = new JPanel();
        JButton play = new JButton("play");
        play.addActionListener(new Menu());
        JButton quit = new JButton("quit");
        quit.addActionListener(new Menu());
        JButton load = new JButton("load and play");
        load.addActionListener(new Menu());
        buttons.add(play);
        buttons.add(quit);
        buttons.add(load);
        add(menuPage);
        add(rules);
        add(buttons);
        setVisible(true);
    }

    //Reference: https://stackoverflow.com/questions/6984099/how-to-fit-a-long-string-into-a-jlabel
    //EFFECT: shows the title and rules for the starting page of the game
    private void titlePage() {
        menuPage = new JLabel("Welcome to King's Row!");
        menuPage.setFont(new Font("Serif", Font.PLAIN, 45));
        String str = "  Rule: Try and get rid of all the cards in your hand or fill both the row of Kings in the least"
                + "amount of round as possible!"
                + "\nThe cards you enter must be cards that follow the pattern of" + "\n - alternating colour"
                + "\n - consecutive descending rank" + "\n You must also make sure that the pattern must continue on"
                + "from the last card in the row. "
                + "\nThe game will start with the Kings of each colour as the starting card.";
        rules = new JTextArea(str, 6, 12);
        rules.setFont(new Font("Arial", Font.ITALIC, 14));
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);
        rules.setOpaque(false);
        rules.setEditable(false);
    }

    //MODIFIES: this
    //EFFECT: initializes the gui of the actual game
    public void playing() {
        initializeHandGui();
        initializeRidGui();
        initializeBlackGui();
        initializeRedGui();
        setGameOptions();
    }

    //MODIFIES: this
    //EFFECT: creates a panel with the buttons for the game page which allows the player
    // to add more cards to their hand, quit the game, or save the game
    private void setGameOptions() {
        gameOptions = new JPanel();
        gameOptions.setLayout(new GridLayout(0,1));
        JButton pass = new JButton("no cards to play");
        pass.addActionListener(new CardGame());
        JButton save = new JButton("save game");
        save.addActionListener(new Menu());
        JButton quitGame = new JButton("quit");
        quitGame.addActionListener(new Menu());
        gameOptions.add(pass);
        gameOptions.add(save);
        gameOptions.add(quitGame);
        add(gameOptions);
        gameOptions.setVisible(true);
    }

    //MODIFIES: this
    //EFFECT: creates the panel for "row 1" which is the row that starts off with the
    // the red king and gives the player the button to add cards to it
    private void initializeRedGui() {
        redPanel = new JPanel();
        JLabel labelRed = new JLabel("ROW 1");
        redPanel.add(labelRed);
        redPanel.setBackground(Color.RED);
        JButton addToRed = new JButton("add to row 1");
        addToRed.addActionListener(new CardGame());
        redPanel.add(addToRed);
        for (int i = 0; i < red.getSize(); i++) {
            JButton redCard = new JButton(red.getCard(i).getImage());
            redPanel.add(redCard);
        }
        add(redPanel, SwingConstants.CENTER);
        redPanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECT: creates the panel for "row 2" which is the row that starts off with the
    // the black king and gives the player the button to add cards to it
    private void initializeBlackGui() {
        blackPanel = new JPanel();
        JLabel labelBlack = new JLabel("ROW 2");
        blackPanel.add(labelBlack);
        blackPanel.setBackground(Color.darkGray);
        JButton addToBlack = new JButton("add to row 2");
        addToBlack.addActionListener(new CardGame());
        blackPanel.add(addToBlack);
        for (int i = 0; i < black.getSize(); i++) {
            JButton blackCard = new JButton(black.getCard(i).getImage());
            blackPanel.add(blackCard);
        }
        add(blackPanel, SwingConstants.CENTER);
        blackPanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECT: creates the panel for the cards the player wants to get rid of so
    //any card they select from their hand will go into this panel
    private void initializeRidGui() {
        ridOfPanel = new JPanel();
        ridOfPanel.setBackground(Color.lightGray);
        add(ridOfPanel, SwingConstants.CENTER);
        ridOfPanel.setVisible(true);
    }

    //MODIFIES: this
    //EFFECT: creates the panel that consists of the buttons that represent the cards in
    //the player's hand
    private void initializeHandGui() {
        handPanel = new JPanel();
        handPanel.setBackground(Color.pink);
        hand.sortDeck();
        for (int i = 0; i < hand.getSize(); i++) {
            JButton handCard = new JButton(hand.getCard(i).toString());
            handCard.addActionListener(new CardGame());
            handPanel.add(handCard);
        }
        add(handPanel, SwingConstants.CENTER);
        handPanel.setVisible(true);
    }

    //EFFECT: if the deck is alternating in colour and is descending in rank
    // when combined with the cards that are already in that row, it returns true
    // otherwise returns false
    public boolean eligible(Deck d, String row) {
        if (row.equals("1")) {
            if (d.getSize() < 3) {
                return false;
            }
            red.addDeck(d);
            red.sortDeck();
            if (red.isAlternatingColour() && red.isDescendingRank()) {
                return true;
            }
            red.removeDeck(d);
        }
        if (row.equals("2")) {
            if (d.getSize() < 3) {
                return false;
            }
            black.addDeck(d);
            black.sortDeck();
            if (black.isAlternatingColour() && black.isDescendingRank()) {
                return true;
            }
            black.removeDeck(d);
        }
        return false;
    }

    //EFFECTS: saves the game to the game.json file in data
    private void saveGame() {
        try {
            jsonWriter.open();
            GameDecks decks = new GameDecks(deck, hand, red, black);
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
            red = decks.getDeck(2);
            black = decks.getDeck(3);
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private class CardGame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            handCardClick(e);
            adding(e, "add to row 1", "1", redPanel);
            adding(e, "add to row 2", "1", blackPanel);
            noCardToPlay(e);
            putBackToHand(e);
        }

        //MODIFIES: this
        //EFFECT: if a button in the ridOfPanel is selected, it puts that card back to the players hand
        private void putBackToHand(ActionEvent e) {
            for (int i = 0; i < getRidOf.getSize(); i++) {
                if (e.getActionCommand().equals(getRidOf.getCard(i) + "?")) {
                    JButton backToHand = new JButton(getRidOf.getCard(i).toString());
                    handPanel.add(backToHand);
                    ridOfPanel.remove(i);
                    hand.addCard(getRidOf.getCard(i));
                    getRidOf.removeCard(getRidOf.getCard(i));
                    validate();
                    repaint();
                }
            }
        }

        //MODIFIES: this
        //EFFECT: if the "no cards to play" is selected it adds three cards to
        //the player's hand from the main deck and add the appropriate buttons to the player's hand,
        //or if the main deck has less than three cards but is not empty, it adds the rest of the
        //cards in the deck to the players and adds the appropriate buttons to the player's hand as well
        //else it checks if the game is over
        private void noCardToPlay(ActionEvent e) {
            if (e.getActionCommand().equals("no cards to play")) {
                if (deck.getSize() > 2) {
                    handPanel.removeAll();
                    hand.removeTopThree(deck);
                    hand.sortDeck();
                    putHandToPanel();
                    validate();
                    repaint();
                } else if (deck.getSize() < 3 && !deck.isEmpty()) {
                    handPanel.removeAll();
                    hand.addDeck(deck);
                    deck.removeAll();
                    hand.sortDeck();
                    putHandToPanel();
                    validate();
                    repaint();
                }
                gameOver();
            }
        }

        //EFFECT: helper method that adds all the cards in the current hand to the handPanel
        private void putHandToPanel() {
            for (int i = 0; i < hand.getSize(); i++) {
                JButton handCard = new JButton(hand.getCard(i).toString());
                handCard.addActionListener(new CardGame());
                handPanel.add(handCard);
            }
        }

        //MODIFIES: this
        //EFFECT: if the deck is empty and the player's hand still has cards left,
        // it shows the player that the game is over
        private void gameOver() {
            if (deck.isEmpty() && !hand.isEmpty()) {
                handPanel.setVisible(false);
                redPanel.setVisible(false);
                blackPanel.setVisible(false);
                ridOfPanel.setVisible(false);
                gameOptions.setVisible(false);
                gameStatus = new JLabel("Game over :(");
                add(gameStatus);
            }
        }

        //MODIFIES: this
        //EFFECT: if the cards in getRidOf are eligible cards to the selected row,
        //it is added to that row's panel and gets rid of the buttons in the ridOfPanel
        //and it also checks if the player has won the game
        private void adding(ActionEvent e, String buttonSelected, String rowNumber, JPanel panel) {
            if (e.getActionCommand().equals(buttonSelected)) {
                if (eligible(getRidOf, rowNumber)) {
                    for (int i = 0; i < getRidOf.getSize(); i++) {
                        JButton button = new JButton(getRidOf.getCard(i).getImage());
                        panel.add(button);
                    }
                } else {
                    hand.addDeck(getRidOf);
                    for (int i = 0; i < getRidOf.getSize(); i++) {
                        JButton button = new JButton(getRidOf.getCard(i).toString());
                        handPanel.add(button);
                    }
                }
                ridOfPanel.removeAll();
                validate();
                repaint();
                getRidOf = new Deck();
                winning();
            }
        }

        //MODIFIES: this
        //EFFECT: if the player's hand if empty or the both the hand and the deck
        //is empty, it show that the player had won the game
        private void winning() {
            if (hand.isEmpty() && deck.isEmpty() || hand.isEmpty()) {
                handPanel.setVisible(false);
                ridOfPanel.setVisible(false);
                gameOptions.setVisible(false);
                gameStatus = new JLabel("YAY! You won! :)");
                add(gameStatus);
            }
        }

        //MODIFIES: this
        //EFFECT: if the cards in the hand is selected, that selected card gets moved to
        // the RidOfPanel and is added to the getRidOf deck
        private void handCardClick(ActionEvent e) {
            for (int i = 0; i < hand.getSize(); i++) {
                if (e.getActionCommand().equals(hand.getCard(i).toString())) {
                    JButton ridButton = new JButton(hand.getCard(i).toString() + "?");
                    ridButton.addActionListener(new CardGame());
                    ridOfPanel.add(ridButton);
                    handPanel.remove(i);
                    getRidOf.addCard(hand.getCard(i));
                    hand.removeCard(hand.getCard(i));
                    validate();
                    repaint();
                }
            }
        }
    }


    private class Menu implements ActionListener {

        //MODIFIES: this
        //EFFECT: if the quit button from the starting page is selected, the game stops running
        // or if the play button is selected, it gets rid of the main starting page and starts the game
        // or if the load and play is selected, it gets rid of the main starting page and starts the with
        // the data that was saved previously
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("quit")) {
                System.exit(0);
            }

            if (e.getActionCommand().equals("play")) {
                buttons.setVisible(false);
                remove(menuPage);
                remove(rules);
                validate();
                repaint();
                playing();
            }

            if (e.getActionCommand().equals("load and play")) {
                buttons.setVisible(false);
                menuPage.setVisible(false);
                rules.setVisible(false);
                loadGame();
                playing();
            }

            if (e.getActionCommand().equals("save game")) {
                saveGame();
            }

        }
    }

    public static void main(String[] args) {
        new GameApp();
    }

}

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

    public void playing() {
        initializeHandGui();
        initializeRidGui();
        initializeBlackGui();
        initializeRedGui();
        setGameOptions();
    }

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

    private void initializeRidGui() {
        ridOfPanel = new JPanel();
        ridOfPanel.setBackground(Color.lightGray);
        add(ridOfPanel, SwingConstants.CENTER);
        ridOfPanel.setVisible(true);
    }

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

        private void putHandToPanel() {
            for (int i = 0; i < hand.getSize(); i++) {
                JButton handCard = new JButton(hand.getCard(i).toString());
                handCard.addActionListener(new CardGame());
                handPanel.add(handCard);
            }
        }

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

        private void adding(ActionEvent e, String s, String s2, JPanel panel) {
            if (e.getActionCommand().equals(s)) {
                if (eligible(getRidOf, s2)) {
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

        private void winning() {
            if (hand.isEmpty() && deck.isEmpty() || hand.isEmpty()) {
                handPanel.setVisible(false);
                ridOfPanel.setVisible(false);
                gameOptions.setVisible(false);
                gameStatus = new JLabel("YAY! You won! :)");
                add(gameStatus);
            }
        }

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

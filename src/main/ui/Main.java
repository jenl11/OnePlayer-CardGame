package ui;


public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to King's Row!");
        String rules = "Rule: Try and get rid of all the cards in your hand or fill both the row of Kings in the least"
                + "amount of round as possible!"
                + "The cards you enter must be cards that follow the pattern of" + "\n - alternating colour"
                + "\n - consecutive descending rank" + "\n You must also make sure that the pattern must continue on"
                + "from the last card in the row. "
                + "\nThe game will start with the Kings of each colour as the starting card.";
        System.out.println(rules);
        Game game = new Game();
        game.play();
    }
}

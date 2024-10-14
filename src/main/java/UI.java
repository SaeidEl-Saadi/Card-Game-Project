import java.io.PrintWriter;
import java.util.Scanner;

public class UI {
    public UI() {}

    public void displayWinners(PrintWriter writer, Game g) {
        writer.print("Game over. Winner(s):\n");
        for (Player p : g.checkWinner()) {
            writer.print(p.getName() + " with " + p.getShields() + " shields\n");
        }
    }

    public void displayDrawnCard(Card c) {
        System.out.print("Drawn Card: " + c.getCard() + "\n");
    }

    public void displayHand(Player p) {
        if (p.getCards().size() == 0) {
            System.out.print(p.getName() + "'s hand is empty\n");
            return;
        }

        System.out.print(p.getName() + "'s hand:\n");
        for (int i = 0; i < p.getCards().size(); i++) {
            System.out.print((i + 1) + ". " + p.getCards().get(i) + "\n");
        }
    }

    public void hotSeatPrompt(Game g) {
        for (int i = 0; i < 20; i++) {
            System.out.print("\n");
        }
        System.out.print(g.getCurrentPlayer().getName() + "'s turn has ended. Press enter for next turn...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.print("\n");
    }

    public String trimPrompt(Player p, Scanner s) {
        System.out.print(p.getName() + " needs to trim their hand (press enter to show hand):\n");
        s.nextLine();

        displayHand(p);

        return s.nextLine();
    }

    public String sponsorPrompt(Player p) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(p.getName() + " do you want to sponsor this quest?\n1. Yes\n2. No\n");

        return scanner.nextLine();
    }
}

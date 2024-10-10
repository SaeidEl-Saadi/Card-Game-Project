import java.io.PrintWriter;

public class UI {
    public UI() {}

    public void displayWinners(PrintWriter writer, Game g) {
        writer.print("Game over. Winner(s):\n");
        for (Player p : g.checkWinner()) {
            writer.print(p.getName() + " with " + p.getShields() + " shields\n");
        }
    }
}

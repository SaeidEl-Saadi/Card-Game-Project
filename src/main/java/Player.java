import java.util.ArrayList;

public class Player {

    ArrayList<Card> cards = new ArrayList<>();

    public Player() {

    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return "";
    }

    public void addCard(Card e) {
        cards.add(e);
    }

    public void giveShields(int amount) {
        return;
    }
}

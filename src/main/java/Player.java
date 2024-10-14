import java.util.ArrayList;

public class Player {

    private ArrayList<Card> cards = new ArrayList<>();
    private String name;
    private int shields = 0;

    public Player(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return this.name;
    }

    public int getShields() {
        return this.shields;
    }

    public void addCard(Card e) {
        cards.add(e);
        cards.sort(new CardComparator());
    }

    public void giveShields(int amount) {
        shields += amount;
    }

    public void removeShields() {
        if (shields < 2) {
            shields = 0;
            return;
        }

        shields -= 2;
    }

    public void removeCard(int index) {
        cards.remove(index - 1);
    }
}

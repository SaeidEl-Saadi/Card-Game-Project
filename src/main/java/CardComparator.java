import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        if (c1 instanceof Foe && c2 instanceof Weapon) {
            return -1;
        } else if (c1 instanceof Weapon && c2 instanceof Foe) {
            return 1;
        } else if (c1 instanceof Foe && c2 instanceof Foe) {
            return Integer.compare(((Foe) c1).getValue(), ((Foe) c2).getValue());
        } else if (c1 instanceof Weapon && c2 instanceof Weapon) {
            if (c1.getCard().equals("S10") && c2.getCard().equals("H10")) {
                return -1;
            } else if (c1.getCard().equals("H10") && c2.getCard().equals("S10")) {
                return 1;
            }

            return Integer.compare(((Weapon) c1).getValue(), ((Weapon) c2).getValue());
        }

        return 0;
    }
}

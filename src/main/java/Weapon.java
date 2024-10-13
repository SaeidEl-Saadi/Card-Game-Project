public class Weapon implements Card {

    private String face;
    private int value;

    public Weapon(String face, int value) {
        this.face = face;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getCard() {
        return face;
    }

    @Override
    public String toString() {
        return face;
    }
}

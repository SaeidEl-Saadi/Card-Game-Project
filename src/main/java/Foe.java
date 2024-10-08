public class Foe implements Card {

    private String face;
    private int value;

    public Foe(String face, int value) {
        this.face = face;
        this.value = value;
    }

    @Override
    public String getCard() {
        return face;
    }
}

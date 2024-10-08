public class Quest implements Card {

    private String face;
    private int stages;

    public Quest(String face, int stages) {
        this.face = face;
        this.stages = stages;
    }

    @Override
    public String getCard() {
        return face;
    }
}
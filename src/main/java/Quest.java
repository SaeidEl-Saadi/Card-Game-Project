import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Quest implements Card {

    private String face;
    private int stageNum;
    private ArrayList<ArrayList<Card>> stages = new ArrayList<ArrayList<Card>>();

    public Quest(String face, int stagesNum) {
        this.face = face;
        this.stageNum = stagesNum;

        for (int i = 0; i < stageNum; i++) {
            stages.add(new ArrayList<Card>());
        }
    }

    @Override
    public String getCard() {
        return face;
    }

    public int getStageNum() {
        return stageNum;
    }

    public ArrayList<ArrayList<Card>> getAllStages() {
        return stages;
    }

    public ArrayList<Card> getStage(int num) {
        return stages.get(num - 1);
    }

    public int computeValue(int stage) {
        int totalValue = 0;

        for (Card c : stages.get(stage - 1)) {
            if (c instanceof Foe) {
                totalValue += ((Foe) c).getValue();
            } else {
                totalValue += ((Weapon) c).getValue();
            }
        }

        return totalValue;
    }

    public int foeNum(int stage) {
        int totalNum = 0;

        for (Card f : stages.get(stage - 1)) {
            if (f instanceof Foe) {
                totalNum += 1;
            }
        }

        return totalNum;
    }

    public boolean containsDuplicateWeapons(int stage) {
        ArrayList<String> faces = new ArrayList<>();

        for (Card c : stages.get(stage - 1)) {
            if (c instanceof Foe) {
                continue;
            }
            faces.add(c.getCard());
        }

        Set<String> duplicates = new HashSet<>(faces);
        if (duplicates.size() != faces.size()) {
            return true;
        }

        return false;
    }

    public int computeCardAmount() {
        int amount = 0;
        for (ArrayList<Card> stage : stages) {
            amount += stage.size();
        }

        return amount;
    }
}
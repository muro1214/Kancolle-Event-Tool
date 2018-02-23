package kancolle.structure;

import java.util.Arrays;
import java.util.List;

import kancolle.fleet.Fleets;

public class Kanmusu {
    private int id;
    private String name;
    private String initialName;
    private ShipType shipType;
    private int level;
    private Speed speed;
    private String tag;

    public Kanmusu(String csvLine) {
        List<String> tmp = Arrays.asList(csvLine.split(",", -1));

        if (tmp.size() != 56) {
            System.err.println("Illegal csv line data.");
            System.err.println("* " + csvLine);
            return;
        }

        id = Integer.parseInt(tmp.get(1));
        name = tmp.get(8);
        initialName = Fleets.getInitialName(tmp.get(10));
        shipType = ShipType.getType(tmp.get(9));
        level = Integer.parseInt(tmp.get(22));
        speed = Speed.getType(tmp.get(25));
//        tag = tmp.get(55);
        tag = "";

        System.out.println(initialName);
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String initialName() {
        return initialName;
    }

    public ShipType shipType() {
        return shipType;
    }

    public String shipTypeString() {
        return shipType.typeName();
    }

    public int level() {
        return level;
    }

    public Speed speed() {
        return speed;
    }

    public String speedString() {
        return speed.string();
    }

    public String tag() {
        return tag;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.format("%s(Lv.%d, ID#%d)", name(), level(), id());
    }
}

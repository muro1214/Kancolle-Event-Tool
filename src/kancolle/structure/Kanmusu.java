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

        this.id = Integer.parseInt(tmp.get(1));
        this.name = tmp.get(8);
        this.initialName = Fleets.getInitialName(tmp.get(10));
        this.shipType = ShipType.getType(tmp.get(9));
        this.level = Integer.parseInt(tmp.get(22));
        this.speed = Speed.getType(tmp.get(25));
        //        tag = tmp.get(55);
        this.tag = "";
    }

    public int id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String initialName() {
        return this.initialName;
    }

    public ShipType shipType() {
        return this.shipType;
    }

    public String shipTypeString() {
        return this.shipType.typeName();
    }

    public int level() {
        return this.level;
    }

    public Speed speed() {
        return this.speed;
    }

    public String speedString() {
        return this.speed.string();
    }

    public String tag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.format("%s(Lv.%d, ID#%d)", this.name, this.level, this.id);
    }
}

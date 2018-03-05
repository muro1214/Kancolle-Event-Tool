package kancolle.structure;

public enum ShipType {
    AIRCRAFT_CARRIER("正規空母"),
    AIRCRAFT_CARRYING_SUBMARINE("潜水空母"),
    AIRCRAFT_CRUISER("航空巡洋艦"),
    AMPHIBIOUS_ASSAULT_SHIP("揚陸艦"),
    ARMORED_AIRCRAFT_CARRIER("装甲空母"),
    AVIATION_BATTLESHIP("航空戦艦"),
    BATTLESHIP("戦艦"),
    DESTROYER("駆逐艦"),
    DESTROYER_ESCORT("海防艦"),
    FLEET_OILER("補給艦"),
    HEAVY_CRUISER("重巡洋艦"),
    LIGHT_AIRCRAFT_CARRIER("軽空母"),
    LIGHT_CRUISER("軽巡洋艦"),
    REPAIR_SHIP("工作艦"),
    SEAPLANE_CARRIER("水上機母艦"),
    SUBMARINE("潜水艦"),
    SUBMARINE_TENDER("潜水母艦"),
    TORPEDO_CRUISER("重雷装巡洋艦"),
    TRAINING_CRUISER("練習巡洋艦");

    private final String typeName;

    private ShipType(final String typeName) {
        this.typeName = typeName;
    }

    public String typeName() {
        return this.typeName;
    }

    public static ShipType getType(final String typeName) {
        ShipType[] types = ShipType.values();
        for (ShipType type : types) {
            if (type.typeName().equals(typeName)) {
                return type;
            }
        }
        return null;
    }
}

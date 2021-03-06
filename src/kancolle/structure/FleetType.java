package kancolle.structure;

public enum FleetType {
    NORMAL("通常艦隊"),
    CARRIER_TASK_FORCE("空母機動"),
    SURFACE_TASK_FORCE("水上打撃"),
    TRANSPORT_ESCORT("輸送連合"),
    STRIKING_FORCE("遊撃艦隊");

    private final String typeName;

    private FleetType(final String name) {
        this.typeName = name;
    }

    public String typeName() {
        return this.typeName;
    }

    public static FleetType getType(final String name) {
        FleetType[] types = FleetType.values();
        for (FleetType type : types) {
            if (type.typeName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}

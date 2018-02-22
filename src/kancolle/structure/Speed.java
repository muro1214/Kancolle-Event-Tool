package kancolle.structure;

public enum Speed {
    FAST("高速"), SLOW("低速");

    private final String string;

    private Speed(final String string) {
        this.string = string;
    }

    public String string() {
        return string;
    }

    public static Speed getType(final String string) {
        Speed[] speeds = Speed.values();
        for (Speed speed : speeds) {
            if (speed.string() == string) {
                return speed;
            }
        }
        return null;
    }
}

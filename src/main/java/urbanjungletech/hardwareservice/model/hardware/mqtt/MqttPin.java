package urbanjungletech.hardwareservice.model.hardware.mqtt;

public class MqttPin {
    private long id;
    private int pin;
    boolean levelable;

    public boolean isLevelable() {
        return levelable;
    }

    public void setLevelable(boolean levelable) {
        this.levelable = levelable;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

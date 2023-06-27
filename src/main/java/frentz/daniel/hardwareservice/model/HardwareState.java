package frentz.daniel.hardwareservice.model;

public class HardwareState {
    private long level;
    private ONOFF state;

    public HardwareState(){
    }

    public HardwareState(ONOFF state, long level){
        this.level = level;
        this.state = state;
    }
    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public ONOFF getState() {
        return state;
    }

    public void setState(ONOFF state) {
        this.state = state;
    }
}

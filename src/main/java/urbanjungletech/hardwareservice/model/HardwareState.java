package urbanjungletech.hardwareservice.model;

public class HardwareState {
    private Long id;
    private long level;
    private String state;
    private Long hardwareId;

    public HardwareState(){
    }

    public HardwareState(String state, long level){
        this.level = level;
        this.state = state;
    }
    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

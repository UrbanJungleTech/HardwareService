package frentz.daniel.hardwareservice.entity;

import frentz.daniel.hardwareservice.client.model.ONOFF;

import jakarta.persistence.*;

@Embeddable
public class HardwareStateEntity {
    private long stateId;
    private long level;
    private ONOFF state;

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


    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }
}

package frentz.daniel.controller.entity;

import frentz.daniel.controllerclient.model.ONOFF;

import javax.persistence.*;

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

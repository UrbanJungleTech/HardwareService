package urbanjungletech.hardwareservice.jsonrpc.model;

import urbanjungletech.hardwareservice.model.HardwareState;

public class HardwareStateRpcMessage {

    public HardwareStateRpcMessage(long port, HardwareState hardwareState){
        this.port = port;
        this.hardwareState = hardwareState;
    }

    private long port;
    private HardwareState hardwareState;

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public HardwareState getHardwareState() {
        return hardwareState;
    }

    public void setHardwareState(HardwareState hardwareState) {
        this.hardwareState = hardwareState;
    }
}

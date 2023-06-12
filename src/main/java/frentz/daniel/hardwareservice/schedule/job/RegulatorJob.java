package frentz.daniel.hardwareservice.schedule.job;

import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.client.model.HardwareState;
import frentz.daniel.hardwareservice.client.model.ONOFF;
import frentz.daniel.hardwareservice.client.model.Regulator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RegulatorJob implements Job {
    private HardwareDAO hardwareDAO;
    private SensorDAO sensorDAO;
    private long id;

    private Regulator regulator;

    private HardwareState onState;
    private HardwareState offState;

    public RegulatorJob(HardwareDAO hardwareDAO,
                        SensorDAO sensorDAO,
                        Regulator regulator){
        this.sensorDAO = sensorDAO;
        this.hardwareDAO = hardwareDAO;
        this.regulator = regulator;
        onState = new HardwareState();
        onState.setLevel(10);
        onState.setState(ONOFF.ON);
        offState = new HardwareState();
        offState.setLevel(10);
        offState.setState(ONOFF.OFF);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

package frentz.daniel.hardwareservice.schedule.factory;

import frentz.daniel.hardwareservice.exception.RegulatorJobAlreadyRunningException;
import frentz.daniel.hardwareservice.schedule.job.RegulatorJob;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.client.model.Regulator;
import org.quartz.*;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegulatorJobFactory extends SimpleJobFactory {

    private Logger logger = LoggerFactory.getLogger(RegulatorJobFactory.class);
    private HardwareDAO hardwareDAO;
    private SensorDAO sensorDAO;

    public RegulatorJobFactory(HardwareDAO hardwareDAO,
                               SensorDAO sensorDAO) {
        this.sensorDAO = sensorDAO;
        this.hardwareDAO = hardwareDAO;

    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        Regulator regulator = (Regulator) bundle.getJobDetail().getJobDataMap().get("regulator");
        if (scheduler.getCurrentlyExecutingJobs().stream().anyMatch((JobExecutionContext job) -> {
            return ((RegulatorJob) job.getJobInstance()).getId() == regulator.getId();
        })) {
            throw new RegulatorJobAlreadyRunningException(regulator.getId());
        }
        RegulatorJob result = new RegulatorJob(this.hardwareDAO, this.sensorDAO, regulator);
        result.setId(regulator.getId());
        return result;
    }
}

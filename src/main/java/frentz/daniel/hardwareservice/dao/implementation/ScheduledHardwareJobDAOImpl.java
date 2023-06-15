package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.ScheduledHardwareJobConverter;
import frentz.daniel.hardwareservice.dao.ScheduledHardwareJobDAO;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.repository.HardwareCronJobRepository;
import frentz.daniel.hardwareservice.client.model.ScheduledHardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledHardwareJobDAOImpl implements ScheduledHardwareJobDAO {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareJobDAOImpl.class);

    private HardwareCronJobRepository hardwareCronJobRepository;
    private ScheduledHardwareJobConverter scheduledHardwareJobConverter;
    public ScheduledHardwareJobDAOImpl(HardwareCronJobRepository cronJobrepository,
                                       ScheduledHardwareJobConverter scheduledHardwareJobConverter){
        this.hardwareCronJobRepository = cronJobrepository;
        this.scheduledHardwareJobConverter = scheduledHardwareJobConverter;
    }

    @Override
    public ScheduledHardwareEntity addScheduledHardwareJob(ScheduledHardware scheduledHardware) {
        logger.debug("Creating scheduled hardware job.");
        ScheduledHardwareEntity scheduledHardwareEntity = new ScheduledHardwareEntity();
        this.scheduledHardwareJobConverter.fillEntity(scheduledHardwareEntity, scheduledHardware);
        return scheduledHardwareEntity;
    }

    @Override
    public List<ScheduledHardwareEntity> getAll() {
        List<ScheduledHardwareEntity> jobs = this.hardwareCronJobRepository.findAll();
        return jobs;
    }

    @Override
    public void delete(long scheduledHardwareId) {
        this.hardwareCronJobRepository.deleteById(scheduledHardwareId);
    }
}

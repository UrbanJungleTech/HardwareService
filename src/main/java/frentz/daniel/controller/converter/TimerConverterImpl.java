package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.TimerEntity;
import frentz.daniel.controllerclient.model.HardwareState;
import frentz.daniel.controllerclient.model.Timer;
import frentz.daniel.model.CronJob;
import frentz.daniel.service.CronClient;
import org.springframework.stereotype.Service;

@Service
public class TimerConverterImpl implements TimerConverter {

    private CronClient cronClient;
    private HardwareStateConverter hardwareStateConverter;

    public TimerConverterImpl(CronClient cronClient, HardwareStateConverter hardwareStateConverter){
        this.cronClient = cronClient;
        this.hardwareStateConverter = hardwareStateConverter;
    }

    @Override
    public Timer toModel(TimerEntity timerEntity) {
        Timer result = new Timer();
        CronJob onCronJob = this.cronClient.getCronJob(timerEntity.getOnCronId());
        result.setOnCronExpression(onCronJob.getCronString());
        CronJob offCronJob = this.cronClient.getCronJob(timerEntity.getOffCronId());
        result.setOffCronExpression(offCronJob.getCronString());
        result.setId(timerEntity.getId());
        return result;
    }
}

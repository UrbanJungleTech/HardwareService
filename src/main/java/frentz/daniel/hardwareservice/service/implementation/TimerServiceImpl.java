package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.client.model.Timer;
import frentz.daniel.hardwareservice.converter.TimerConverter;
import frentz.daniel.hardwareservice.dao.TimerDAO;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.service.TimerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimerServiceImpl implements TimerService {

    private TimerDAO timerDAO;
    private TimerConverter timerConverter;
    public TimerServiceImpl(TimerDAO timerDAO,
                            TimerConverter timerConverter) {
        this.timerDAO = timerDAO;
        this.timerConverter = timerConverter;
    }

    @Override
    public Timer getTimer(long id) {
        TimerEntity timerEntity = this.timerDAO.getTimer(id);
        Timer result = this.timerConverter.toModel(timerEntity);
        return result;
    }

    @Override
    public List<Timer> getTimers() {
        List<TimerEntity> timerEntities = this.timerDAO.getTimers();
        List<Timer> result = this.timerConverter.toModels(timerEntities);
        return result;
    }
}

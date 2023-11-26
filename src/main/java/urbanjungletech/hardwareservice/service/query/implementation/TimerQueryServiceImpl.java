package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.TimerConverter;
import urbanjungletech.hardwareservice.dao.TimerDAO;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.service.query.TimerQueryService;

import java.util.List;

@Service
public class TimerQueryServiceImpl implements TimerQueryService {

    private final TimerDAO timerDAO;
    private final TimerConverter timerConverter;
    public TimerQueryServiceImpl(TimerDAO timerDAO,
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

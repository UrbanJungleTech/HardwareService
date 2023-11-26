package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.dao.AlertConditionDAO;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.List;

@Service
public class AlertConditionAdditionServiceImpl implements AlertConditionAdditionService {

    private final AlertConditionDAO alertConditionDAO;
    private final AlertConditionConverter alertConditionConverter;

    public AlertConditionAdditionServiceImpl(AlertConditionDAO alertConditionDAO,
                                             AlertConditionConverter alertConditionConverter){
        this.alertConditionDAO = alertConditionDAO;
        this.alertConditionConverter = alertConditionConverter;
    }
    @Override
    public AlertCondition create(AlertCondition alertCondition) {
        AlertConditionEntity alertConditionEntity = this.alertConditionDAO.create(alertCondition);
        AlertCondition result = this.alertConditionConverter.toModel(alertConditionEntity);
        return result;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public AlertCondition update(long id, AlertCondition alertCondition) {
        return null;
    }

    @Override
    public List<AlertCondition> updateList(List<AlertCondition> models) {
        return null;
    }
}

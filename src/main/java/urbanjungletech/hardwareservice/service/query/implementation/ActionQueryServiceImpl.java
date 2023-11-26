package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.AlertActionConverter;
import urbanjungletech.hardwareservice.dao.AlertActionDAO;
import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.service.query.ActionQueryService;

import java.util.List;

@Service
public class ActionQueryServiceImpl implements ActionQueryService {

    private final AlertActionDAO alertActionDAO;
    private final AlertActionConverter alertActionConverter;

    public ActionQueryServiceImpl(AlertActionDAO alertActionDAO,
                                  AlertActionConverter alertActionConverter){
        this.alertActionDAO = alertActionDAO;
        this.alertActionConverter = alertActionConverter;
    }
    @Override
    public AlertAction getAction(long actionId) {
        AlertActionEntity alertActionEntity = this.alertActionDAO.getAction(actionId);
        return this.alertActionConverter.toModel(alertActionEntity);
    }

    @Override
    public List<AlertAction> getAllActions() {
        List<AlertActionEntity> actionEntities = this.alertActionDAO.getAllActions();
        List<AlertAction> result = actionEntities.stream().map(
                actionEntity -> this.alertActionConverter.toModel(actionEntity)
        ).toList();
        return result;
    }
}

package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.AlertActionAdditionService;
import urbanjungletech.hardwareservice.converter.alert.action.AlertActionConverter;
import urbanjungletech.hardwareservice.dao.AlertActionDAO;
import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

import java.util.List;

@Service
public class AlertAlertActionAdditionServiceImpl implements AlertActionAdditionService {

    private final AlertActionDAO alertActionDAO;
    private final AlertActionConverter alertActionConverter;
    public AlertAlertActionAdditionServiceImpl(AlertActionDAO alertActionDAO,
                                               AlertActionConverter alertActionConverter){
        this.alertActionDAO = alertActionDAO;
        this.alertActionConverter = alertActionConverter;
    }
    @Transactional
    @Override
    public AlertAction create(AlertAction alertAction) {
        AlertActionEntity alertActionEntity = this.alertActionDAO.create(alertAction);
        AlertAction result = this.alertActionConverter.toModel(alertActionEntity);
        return result;
    }

    @Transactional
    @Override
    public void delete(long id) {
        this.alertActionDAO.deleteAction(id);
    }

    @Transactional
    @Override
    public AlertAction update(long id, AlertAction alertAction) {
        return null;
    }

    @Transactional
    @Override
    public List<AlertAction> updateList(List<AlertAction> models) {
        return null;
    }
}

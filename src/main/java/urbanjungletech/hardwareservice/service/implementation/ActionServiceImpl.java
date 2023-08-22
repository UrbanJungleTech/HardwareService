package urbanjungletech.hardwareservice.service.implementation;

import urbanjungletech.hardwareservice.action.converter.ActionConverter;
import urbanjungletech.hardwareservice.action.entity.ActionEntity;
import urbanjungletech.hardwareservice.action.model.Action;
import urbanjungletech.hardwareservice.dao.ActionDAO;
import urbanjungletech.hardwareservice.service.ActionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {

    private ActionDAO actionDAO;
    private ActionConverter actionConverter;

    public ActionServiceImpl(ActionDAO actionDAO,
                             ActionConverter actionConverter){
        this.actionDAO = actionDAO;
        this.actionConverter = actionConverter;
    }
    @Override
    public Action getAction(long actionId) {
        ActionEntity actionEntity = this.actionDAO.getAction(actionId);
        return this.actionConverter.toModel(actionEntity);
    }

    @Override
    public List<Action> getAllActions() {
        List<ActionEntity> actionEntities = this.actionDAO.getAllActions();
        List<Action> result = actionEntities.stream().map(
                actionEntity -> this.actionConverter.toModel(actionEntity)
        ).toList();
        return result;
    }
}

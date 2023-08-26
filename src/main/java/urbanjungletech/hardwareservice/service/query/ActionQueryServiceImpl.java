package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.converter.ActionConverter;
import urbanjungletech.hardwareservice.entity.ActionEntity;
import urbanjungletech.hardwareservice.model.Action;
import urbanjungletech.hardwareservice.dao.ActionDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionQueryServiceImpl implements ActionQueryService {

    private ActionDAO actionDAO;
    private ActionConverter actionConverter;

    public ActionQueryServiceImpl(ActionDAO actionDAO,
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

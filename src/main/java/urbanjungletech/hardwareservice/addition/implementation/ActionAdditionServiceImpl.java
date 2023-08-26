package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.converter.ActionConverter;
import urbanjungletech.hardwareservice.entity.ActionEntity;
import urbanjungletech.hardwareservice.model.Action;
import urbanjungletech.hardwareservice.addition.ActionAdditionService;
import urbanjungletech.hardwareservice.dao.ActionDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActionAdditionServiceImpl implements ActionAdditionService {

    private ActionDAO actionDAO;
    private ActionConverter actionConverter;
    public ActionAdditionServiceImpl(ActionDAO actionDAO,
                                     ActionConverter actionConverter){
        this.actionDAO = actionDAO;
        this.actionConverter = actionConverter;
    }
    @Transactional
    @Override
    public Action create(Action action) {
        ActionEntity actionEntity = this.actionDAO.create(action);
        Action result = this.actionConverter.toModel(actionEntity);
        return result;
    }

    @Transactional
    @Override
    public void delete(long id) {
        this.actionDAO.deleteAction(id);
    }

    @Transactional
    @Override
    public Action update(long id, Action action) {
        return null;
    }

    @Transactional
    @Override
    public List<Action> updateList(List<Action> models) {
        return null;
    }
}

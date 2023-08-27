package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.dao.HardwareStateDAO;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.event.hardwarestate.HardwareStateEventPublisher;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.HardwareStateType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HardwareStateAdditionServiceImpl implements HardwareStateAdditionService {
    private HardwareStateDAO hardwareStateDAO;
    private HardwareStateConverter hardwareStateConverter;
    private HardwareStateEventPublisher hardwareStateEventPublisher;

    public HardwareStateAdditionServiceImpl(HardwareStateDAO hardwareStateDAO,
                                            HardwareStateConverter hardwareStateConverter,
                                            HardwareStateEventPublisher hardwareStateEventPublisher) {
        this.hardwareStateDAO = hardwareStateDAO;
        this.hardwareStateConverter = hardwareStateConverter;
        this.hardwareStateEventPublisher = hardwareStateEventPublisher;
    }

    @Transactional
    @Override
    public HardwareState create(HardwareState hardwareState, HardwareStateType hardwareStateType) {
        HardwareStateEntity hardwareStateEntity = this.hardwareStateDAO.createHardwareState(hardwareState, hardwareStateType);
        return this.hardwareStateConverter.toModel(hardwareStateEntity);
    }

    @Transactional
    @Override
    public HardwareState update(long hardwareStateId, HardwareState hardwareState) {
        hardwareState.setId(hardwareStateId);
        HardwareStateEntity currentHardwareState = this.hardwareStateDAO.findById(hardwareStateId);
        boolean updateState = currentHardwareState.getState() != hardwareState.getState() || currentHardwareState.getLevel() != hardwareState.getLevel();
        HardwareStateEntity hardwareStateEntity = this.hardwareStateDAO.updateHardwareState(hardwareState);
        HardwareState result = this.hardwareStateConverter.toModel(hardwareStateEntity);
        if(hardwareStateEntity.getHardware().getDesiredState().getId() == hardwareStateEntity.getId()
    && updateState){
            this.hardwareStateEventPublisher.publishHardwareStateUpdateEvent(hardwareStateEntity.getId());
        }
        return result;
    }
}
